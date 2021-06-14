package com.site.plazam.service.impl;

import com.site.plazam.domain.Day;
import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Movie;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;
import com.site.plazam.dto.parents.SeanceSimpleDTO;
import com.site.plazam.error.TimeAlreadyScheduledException;
import com.site.plazam.repository.SeanceRepository;
import com.site.plazam.service.*;
import com.site.plazam.service.mapper.SeanceMapper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private final SeanceRepository sr;

    private final SeanceMapper sm;

    private final MongoTemplate mt;

    @Lazy
    private final UserService us;

    @Lazy
    private final MessageService mss;

    @Lazy
    private final HallService hs;

    @Lazy
    private final TicketService ts;

    @Lazy
    private final MovieService ms;

    public SeanceServiceImpl(SeanceRepository seanceRepository,
                             SeanceMapper seanceMapper,
                             @Lazy HallService hallService,
                             @Lazy TicketService ticketService,
                             @Lazy MovieService movieService,
                             @Lazy UserService userService,
                             @Lazy MessageService messageService,
                             MongoTemplate mongoTemplate) {
        this.sr = seanceRepository;
        this.sm = seanceMapper;
        this.hs = hallService;
        this.ts = ticketService;
        this.ms = movieService;
        this.mt = mongoTemplate;
        this.us = userService;
        this.mss = messageService;
    }

    @Override
    @Transactional
    public PagedListHolder findSeancesList(LocalDate currentDate,
                                           CinemaDTO cinema,
                                           List<String> technologies,
                                           List<String> genres,
                                           boolean singleDate,
                                           MovieForSeanceDTO currentMovie,
                                           Pageable pageable) {
        List<HallForSeanceDTO> availableHalls =
                hs.findHallForSeanceByCinema(cinema);
        if (technologies != null) {
            List<Technology> techsList =
                    technologies.stream().map(Technology::valueOf).collect(Collectors.toList());
            availableHalls =
                    availableHalls.stream().filter(hall -> techsList.contains(hall.getTechnology())).collect(Collectors.toList());
        }
        List<SeanceForSeancesListDTO> availableSeances =
                findByDateFromBeforeEqualsAndDateToAfterEqualsAndHalls(currentDate,
                        currentDate, availableHalls);
        if (currentMovie != null) {
            availableSeances =
                    availableSeances.stream().filter(seance -> seance.getMovie().equals(currentMovie)).collect(Collectors.toList());
        }
        if (singleDate) {
            availableSeances =
                    availableSeances.stream().filter(seance -> seance.getDays()
                            .contains(Day.valueOf(currentDate.getDayOfWeek().toString())))
                            .collect(Collectors.toList());
        }
        if (genres != null) {
            List<MovieForSeanceDTO> moviesByGenre =
                    ms.findMovieForSeanceByGenresIsContaining(genres.stream().map(Genre::valueOf).collect(Collectors.toList()));
            availableSeances =
                    availableSeances.stream().filter(seance -> moviesByGenre.contains(seance.getMovie())).collect(Collectors.toList());
        }
        Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
                List<SeanceForSeancesListDTO>>>> mapOfSchedule = new HashMap<>();

//        availableSeances.forEach(seance -> {
//            mapOfSchedule.put(seance.getMovie(), new HashMap<>());
//            for (int i = 0; i < (singleDate ? 1 : 7); i++) {
//                mapOfSchedule.get(seance.getMovie()).put(currentDate.plusDays(i), new HashMap<>());
//            }
//        });

        int max = 1;
        for (SeanceForSeancesListDTO seance : availableSeances) {
            if (!mapOfSchedule.containsKey(seance.getMovie())) {
                mapOfSchedule.put(seance.getMovie(), new HashMap<>());
            }
            for (int i = 0; i < (singleDate ? 1 :
                    ChronoUnit.DAYS.between(LocalDateTime.of(LocalDate.now(),
                            LocalTime.of(0, 0)), LocalDateTime.of(seance.getDateTo(),
                            LocalTime.of(0, 0))) + 1); i++) {
                max = Math.max(max, i);
                mapOfSchedule.get(seance.getMovie()).put(currentDate.plusDays(i), new HashMap<>());
            }
        }

        for (int i = 0; i < (singleDate ? 1 : max); i++) {
            LocalDate current = currentDate.plusDays(i);
            availableSeances.forEach(seance -> {
                if ((seance.getDateFrom().isBefore(current) || seance.getDateFrom().isEqual(current))
                        && (seance.getDateTo().isAfter(current) || seance.getDateTo().isEqual(current))
                        && seance.getDays().contains(Day.valueOf(current.getDayOfWeek().toString()))) {

                    if (!mapOfSchedule.get(seance.getMovie()).get(current).containsKey(seance.getHall())) {
                        mapOfSchedule.get(seance.getMovie()).get(current).put(seance.getHall(), new ArrayList<>());
                    }
                    mapOfSchedule.get(seance.getMovie()).get(current).get(seance.getHall()).add(seance);
                }
            });
        }

//        mapOfSchedule.keySet().forEach(movie -> mapOfSchedule.get(movie).keySet().forEach(date -> {
//            if (mapOfSchedule.get(movie).get(date).isEmpty()) {
//                mapOfSchedule.get(movie).remove(date);
//            }
//        }));
        Iterator<MovieForSeanceDTO> iter = mapOfSchedule.keySet().iterator();
        MovieForSeanceDTO movie;
        Map<LocalDate, Map<HallForSeanceDTO,
                List<SeanceForSeancesListDTO>>> newDateMap;
        while (iter.hasNext()) {
            movie = iter.next();
            newDateMap = new HashMap<>();
            Iterator<LocalDate> dateIter =
                    mapOfSchedule.get(movie).keySet().iterator();
            LocalDate date;
            while (dateIter.hasNext()) {
                date = dateIter.next();
                if (!mapOfSchedule.get(movie).get(date).isEmpty()) {
                    newDateMap.put(date, mapOfSchedule.get(movie).get(date));
                }
            }
            if (newDateMap.isEmpty()) {
                mapOfSchedule.remove(movie);
            } else {
                mapOfSchedule.put(movie, newDateMap);
            }
        }
//        mapOfSchedule.keySet().forEach(movie -> {
//            Map<LocalDate, Map<HallForSeanceDTO,
//                    List<SeanceForSeancesListDTO>>> newDateMap =
//                    new HashMap<>();
//            mapOfSchedule.get(movie).keySet().forEach(date -> {
//                if (!mapOfSchedule.get(movie).get(date).isEmpty()) {
//                    newDateMap.put(date, mapOfSchedule.get(movie).get(date));
//                }
//            });
//            if (newDateMap.isEmpty()) {
//                mapOfSchedule.remove(movie);
//            }
//            //todo simplify from stream|iterator???
//        });
        PagedListHolder seancesPage =
                new PagedListHolder(new ArrayList<>(mapOfSchedule.entrySet()));
        seancesPage.setPage(pageable.getPageNumber());
        seancesPage.setPageSize(pageable.getPageSize());

        return seancesPage;
    }

    @Override
    @Transactional
    public SeanceForSeancesListDTO save(SeanceCreateDTO seanceCreateDTO) throws TimeAlreadyScheduledException {
        seanceCreateDTO.setStartSeance(seanceCreateDTO.getStartSeance().withYear(1)
                .withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0));
        seanceCreateDTO.setEndSeance(seanceCreateDTO.getEndSeance().withYear(1)
                .withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0));
        MovieForMoviesListDTO movie =
                ms.findMovieForMoviesListById(seanceCreateDTO.getMovie().getId());
        //todo if movie release date later than seance dateFrom - throw error
        //todo if seance startTime earlier 5.00 - throw error
        if (seanceCreateDTO.getEndSeance() == null) {
            seanceCreateDTO.setEndSeance(seanceCreateDTO.getStartSeance().plusMinutes(movie.getDurationInMinutes()));
        }
        List<SeanceForSeancesListDTO> listByTime1 =
                findByStartSeanceBetweenOrEndSeanceBetween(seanceCreateDTO.getStartSeance(),
                        seanceCreateDTO.getEndSeance());
        List<SeanceForSeancesListDTO> listByTime2 =
                findByStartSeanceBeforeAndEndSeanceAfter(seanceCreateDTO.getStartSeance(),
                        seanceCreateDTO.getEndSeance());
        List<SeanceForSeancesListDTO> list1 =
                findByDateFromBetweenOrDateToBetween(seanceCreateDTO.getDateFrom(),
                        seanceCreateDTO.getDateTo()).stream()
                        .filter(seance -> listByTime1.contains(seance)
                                && seance.getHall().getId().equals(seanceCreateDTO.getHall().getId())
                                && seance.getDays().stream().anyMatch(seanceCreateDTO.getDays()::contains))
                        .collect(Collectors.toList());
        List<SeanceForSeancesListDTO> list2 =
                findByDateFromBeforeAndDateToAfter(seanceCreateDTO.getDateFrom(),
                        seanceCreateDTO.getDateTo()).stream().filter(seance -> listByTime2.contains(seance)
                        && seance.getHall().getId().equals(seanceCreateDTO.getHall().getId())
                        && seance.getDays().stream().anyMatch(seanceCreateDTO.getDays()::contains))
                        .collect(Collectors.toList());
        List<SeanceForSeancesListDTO> list3 =
                findByDateFromBetweenOrDateToBetween(seanceCreateDTO.getDateFrom(),
                        seanceCreateDTO.getDateTo()).stream()
                        .filter(seance -> listByTime2.contains(seance)
                                && seance.getHall().getId().equals(seanceCreateDTO.getHall().getId())
                                && seance.getDays().stream().anyMatch(seanceCreateDTO.getDays()::contains))
                        .collect(Collectors.toList());
        List<SeanceForSeancesListDTO> list4 =
                findByDateFromBeforeAndDateToAfter(seanceCreateDTO.getDateFrom(),
                        seanceCreateDTO.getDateTo()).stream().filter(seance -> listByTime1.contains(seance)
                        && seance.getHall().getId().equals(seanceCreateDTO.getHall().getId())
                        && seance.getDays().stream().anyMatch(seanceCreateDTO.getDays()::contains))
                        .collect(Collectors.toList());
        if (!list1.isEmpty() || !list2.isEmpty() || !list3.isEmpty() || !list4.isEmpty()) {
            throw new TimeAlreadyScheduledException(seanceCreateDTO);
        }
        if (!movie.getAvailableTechnologies().contains(seanceCreateDTO.getHall().getTechnology())) {
            ms.addAvailableTechnology(movie, seanceCreateDTO.getHall().getTechnology());
        }
        if (seanceCreateDTO.getId() != null) {
            ts.findBySeance(findSeanceForTicketById(seanceCreateDTO.getId()))
                    .forEach(ticket -> {
                        MessageCreateDTO message = new MessageCreateDTO();
                        message.setText(new HashMap<String, String>() {{
                            put("eng", "Seance on movie " +
                                    ticket.getSeance().getMovie().getName() + " " +
                                    ticket.getSeance().getMovie().getSurname() +
                                    " has been changed. If you have already paid" +
                                    " for the ticket, your money will be " +
                                    "refunded.");
                            put("ukr", "Сеанс на фільм " +
                                    ticket.getSeance().getMovie().getName() + " " +
                                    ticket.getSeance().getMovie().getSurname() +
                                    " було змінено. Якщо ви уже оплатили квиток, " +
                                    "ваші гроші будуть повернуті.");
                            put("pol", "Projekcja filmu " +
                                    ticket.getSeance().getMovie().getName() + " " +
                                    ticket.getSeance().getMovie().getSurname() +
                                    " została zmieniona. Jeśli już zapłaciłeś za " +
                                    "bilet, Twoje pieniądze zostaną zwrócone.");
                        }});
                        MessageForUserDTO userMessage = mss.save(message);
                        UserForSelfInfoDTO user =
                                us.findByTicketsContains(ticket);
                        user.getMessages().add(userMessage);
                        us.updateLists(user);
                    });
        }
        return sm.toSeanceForSeancesListDTO(sr.save(sm.toEntity(seanceCreateDTO)));
    }

    @Override
    public void deleteByDateToBefore(LocalDate date) {
        sr.deleteByDateToBefore(date);
    }

    @Override
    public SeanceForSeancesListDTO findSeanceForSeancesListById(String id) {
        return sr.findById(id).map(sm::toSeanceForSeancesListDTO).orElse(null);
    }

    @Override
    public SeanceForTicketDTO findSeanceForTicketById(String id) {
        return sr.findById(id).map(sm::toSeanceForTicketDTO).orElse(null);
    }

    @Override
    public List<SeanceForSeancesListDTO> findSeanceForSeancesListAll() {
        return sr.findAll().stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<SeanceForSeancesListDTO> findSeanceForSeancesListAll(Pageable pageable) {
        return sr.findAll(pageable).map(sm::toSeanceForSeancesListDTO);
    }

    @Override
    public List<SeanceForSeancesListDTO> findByHall(HallForSeanceDTO hall) {
        return sr.findByHallId(hall.getId()).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<SeanceForSeancesListDTO> findByHall(HallForSeanceDTO hall, Pageable pageable) {
        return sr.findByHallId(hall.getId(), pageable).map(sm::toSeanceForSeancesListDTO);
    }

    @Override
    public List<SeanceForSeancesListDTO> findByMovie(MovieForSeanceDTO movie) {
        return sr.findByMovieId(movie.getId()).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDayContains(Day day) {
        return sr.findByDaysContains(day).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfterAndHalls(LocalDate date, LocalDate date2, List<HallForSeanceDTO> halls) {
        return sr.findByDateFromBeforeAndDateToAfterAndHallIdIsIn(date,
                date2, halls.stream().map(HallSimpleDTO::getId).collect(Collectors.toList()))
                .stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDateFromBeforeEqualsAndDateToAfterEqualsAndHalls(LocalDate date, LocalDate date2, List<HallForSeanceDTO> halls) {
        return sr.findByDateFromBeforeEqualsAndDateToAfterEqualsAndHallIdIsIn(date,
                date2, halls.stream().map(HallSimpleDTO::getId).collect(Collectors.toList()))
                .stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public Page<SeanceForSeancesListDTO> findByDateFromBeforeEqualsAndDateToAfterEqualsAndHalls(LocalDate date, LocalDate date2, List<HallForSeanceDTO> halls, List<String> technologies,
                                                                                                List<String> genres, Pageable pageable) {
        List<SeanceForSeancesListDTO> seances = sr.findByDateFromBeforeEqualsAndDateToAfterEqualsAndHallIdIsIn(date,
                date2,
                halls.stream().map(HallSimpleDTO::getId).collect(Collectors.toList())).stream()
                .map(sm::toSeanceForSeancesListDTO).filter(seance -> {
                    if (technologies == null && genres == null) {
                        return true;
                    } else if (technologies == null) {
                        return ms.findMovieFullById(seance.getMovie().getId()).getGenres().stream().map(Genre::name).anyMatch(genres::contains);
                    } else if (genres == null) {
                        return technologies.contains(seance.getHall().getTechnology().name());
                    } else {
                        return technologies.contains(seance.getHall().getTechnology().name()) &&
                                ms.findMovieFullById(seance.getMovie().getId()).getGenres().stream().map(Genre::name).anyMatch(genres::contains);
                    }
                }).collect(Collectors.toList());
        return new PageImpl<>(seances, pageable, seances.size());
    }

    @Override
    public Page<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfter(LocalDate date, LocalDate date2, Pageable pageable) {
        return sr.findByDateFromBeforeAndDateToAfter(date, date2, pageable).map(sm::toSeanceForSeancesListDTO);
    }

    @Override
    public Page<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfterAndHalls(LocalDate date, LocalDate date2, List<HallForSeanceDTO> halls, Pageable pageable) {
        return sr.findByDateFromBeforeAndDateToAfterAndHallIdIsIn(date, date2,
                halls.stream().map(HallSimpleDTO::getId).collect(Collectors.toList()),
                pageable).map(sm::toSeanceForSeancesListDTO);
    }

    @Override
    public List<SeanceForSeancesListDTO> findByStartSeanceBetweenOrEndSeanceBetween(LocalDateTime start, LocalDateTime end) {
        start = start.withYear(1).withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0);
        end = end.withYear(1).withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0);
        return sr.findByStartSeanceBetweenOrEndSeanceBetween(start, end,
                start, end).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByStartSeanceBeforeAndEndSeanceAfter(LocalDateTime start, LocalDateTime end) {
        start = start.withYear(1).withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0);
        end = end.withYear(1).withMonth(1).withDayOfMonth(1).withSecond(0).withNano(0);
        return sr.findByStartSeanceBeforeAndEndSeanceAfter(start, end).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDateFromBetweenOrDateToBetween(LocalDate start, LocalDate end) {
        return sr.findByDateFromBetweenOrDateToBetween(start, end, start,
                end).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfter(LocalDate start, LocalDate end) {
        return sr.findByDateFromBeforeAndDateToAfter(start, end).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(SeanceSimpleDTO seance) {
        SeanceForTicketDTO seanceSearch = new SeanceForTicketDTO();
        seanceSearch.setId(seance.getId());
        SeanceForSeancesListDTO seanceSearch2 =
                findSeanceForSeancesListById(seance.getId());
        Set<Technology> technologies =
                findByMovie(seanceSearch2.getMovie())
                        .stream().filter(seanceDTO -> !seanceDTO.equals(seanceSearch2))
                        .map(seanceDTO -> seanceDTO.getHall().getTechnology())
                        .collect(Collectors.toSet());
        if (!technologies.contains(seanceSearch2.getHall().getTechnology())) {
            ms.removeAvailableTechnology(seanceSearch2.getMovie(),
                    seanceSearch2.getHall().getTechnology());
        }
        ts.findBySeance(seanceSearch).forEach(ts::delete);
        //todo if tickets reserved money back
        sr.deleteById(seance.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        Query query = new Query();
        Update update = new Update().set("availableTechnologies",
                new HashSet<>());
        mt.findAndModify(query, update, Movie.class);
        ts.deleteAll();
        sr.deleteAll();
    }
}
