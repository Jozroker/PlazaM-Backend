package com.site.plazam.service.impl;

import com.site.plazam.domain.Day;
import com.site.plazam.domain.Movie;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;
import com.site.plazam.dto.parents.SeanceSimpleDTO;
import com.site.plazam.error.TimeAlreadyScheduledException;
import com.site.plazam.repository.SeanceRepository;
import com.site.plazam.service.HallService;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.SeanceService;
import com.site.plazam.service.TicketService;
import com.site.plazam.service.mapper.SeanceMapper;
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
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private final SeanceRepository sr;

    private final SeanceMapper sm;

    private final MongoTemplate mt;

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
                             MongoTemplate mongoTemplate) {
        this.sr = seanceRepository;
        this.sm = seanceMapper;
        this.hs = hallService;
        this.ts = ticketService;
        this.ms = movieService;
        this.mt = mongoTemplate;
    }

    @Override
    @Transactional
    public Page<Map.Entry> findSeancesList(LocalDate currentDate,
                                           CinemaDTO cinema, Pageable pageable) {
        List<HallForSeanceDTO> availableHalls =
                hs.findHallForSeanceByCinema(cinema);
        List<SeanceForSeancesListDTO> availableSeances =
                findByDateFromBeforeEqualsAndDateToAfterEqualsAndHalls(currentDate,
                        currentDate, availableHalls);
        Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
                List<SeanceForSeancesListDTO>>>> mapOfSchedule = new HashMap<>();

        availableSeances.forEach(seance -> {
            mapOfSchedule.put(seance.getMovie(), new HashMap<>());
            for (int i = 0; i < 7; i++) {
                mapOfSchedule.get(seance.getMovie()).put(currentDate.plusDays(i), new HashMap<>());
            }
        });

        for (int i = 0; i < 7; i++) {
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
        mapOfSchedule.keySet().forEach(movie -> {
            Map<LocalDate, Map<HallForSeanceDTO,
                    List<SeanceForSeancesListDTO>>> newDateMap =
                    new HashMap<>();
            mapOfSchedule.get(movie).keySet().forEach(date -> {
                if (!mapOfSchedule.get(movie).get(date).isEmpty()) {
                    newDateMap.put(date, mapOfSchedule.get(movie).get(date));
                }
            });
            mapOfSchedule.put(movie, newDateMap);
            //todo simplify from stream|iterator???
        });

        return new PageImpl<>(new ArrayList<>(mapOfSchedule.entrySet()),
                pageable, mapOfSchedule.entrySet().size());
    }

    @Override
    @Transactional
    public SeanceForSeancesListDTO save(SeanceCreateDTO seanceCreateDTO) throws TimeAlreadyScheduledException {
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
        return sm.toSeanceForSeancesListDTO(sr.save(sm.toEntity(seanceCreateDTO)));
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
    public List<SeanceForSeancesListDTO> findByStartSeanceBetweenOrEndSeanceBetween(LocalTime start, LocalTime end) {
        return sr.findByStartSeanceBetweenOrEndSeanceBetween(start, end,
                start, end).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByStartSeanceBeforeAndEndSeanceAfter(LocalTime start, LocalTime end) {
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
