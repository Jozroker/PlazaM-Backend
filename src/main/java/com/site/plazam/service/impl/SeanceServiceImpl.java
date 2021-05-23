package com.site.plazam.service.impl;

import com.site.plazam.domain.Day;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;
import com.site.plazam.dto.parents.SeanceSimpleDTO;
import com.site.plazam.repository.SeanceRepository;
import com.site.plazam.service.HallService;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.SeanceService;
import com.site.plazam.service.mapper.SeanceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeanceServiceImpl implements SeanceService {

    private final SeanceRepository sr;

    private final MovieService ms;

    private final HallService hs;

    private final SeanceMapper sm;

    public SeanceServiceImpl(SeanceRepository seanceRepository,
                             SeanceMapper seanceMapper,
                             MovieService movieService,
                             HallService hallService) {
        this.sr = seanceRepository;
        this.sm = seanceMapper;
        this.ms = movieService;
        this.hs = hallService;
    }

    @Override
    @Transactional
    public Page<Map.Entry> findSeancesList(LocalDate currentDate,
                                           CinemaDTO cinema) {
        List<HallForSeanceDTO> availableHalls =
                hs.findHallForSeanceByCinemaId(cinema.getId());
        List<SeanceForSeancesListDTO> availableSeances =
                findByDateFromBeforeAndDateToAfterAndHalls(currentDate,
                        currentDate, availableHalls);
        Map<MovieForSeanceDTO, Map<LocalDate, Map<HallForSeanceDTO,
                List<SeanceForSeancesListDTO>>>> mapOfSchedule = new HashMap<>();

        availableSeances.forEach(seance -> {
            mapOfSchedule.put(seance.getMovie(), new HashMap<>());
            for (int i = 0; i < 6; i++) {
                mapOfSchedule.get(seance.getMovie()).put(currentDate.plusDays(i), new HashMap<>());
            }
        });

        for (int i = 0; i < 6; i++) {
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

        return new PageImpl<>(new ArrayList<>(mapOfSchedule.entrySet()));
    }

    @Override
    public SeanceForSeancesListDTO save(SeanceCreateDTO seanceCreateDTO) {
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
    public List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfter(LocalDate date, LocalDate date2) {
        return sr.findByDateFromBeforeAndDateToAfter(date, date2).stream().map(sm::toSeanceForSeancesListDTO).collect(Collectors.toList());
    }

    @Override
    public List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfterAndHalls(LocalDate date, LocalDate date2, List<HallForSeanceDTO> halls) {
        return sr.findByDateFromBeforeAndDateToAfterAndHallIdIsIn(date,
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
    public void delete(SeanceSimpleDTO seance) {
        sr.deleteById(seance.getId());
    }

    @Override
    public void deleteAll() {
        sr.deleteAll();
    }
}
