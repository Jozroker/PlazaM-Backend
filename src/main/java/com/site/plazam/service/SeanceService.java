package com.site.plazam.service;

import com.site.plazam.domain.Day;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.SeanceSimpleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SeanceService {

    SeanceForSeancesListDTO save(SeanceCreateDTO seanceCreateDTO);

    SeanceForSeancesListDTO findSeanceForSeancesListById(String id);

    SeanceForTicketDTO findSeanceForTicketById(String id);

    List<SeanceForSeancesListDTO> findSeanceForSeancesListAll();

    Page<SeanceForSeancesListDTO> findSeanceForSeancesListAll(Pageable pageable);

    List<SeanceForSeancesListDTO> findByHall(HallForSeanceDTO hall);

    Page<SeanceForSeancesListDTO> findByHall(HallForSeanceDTO hall, Pageable pageable);

    List<SeanceForSeancesListDTO> findByMovie(MovieForSeanceDTO movie);

    List<SeanceForSeancesListDTO> findByDayContains(Day day);

    List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfter(LocalDate date,
                                                                     LocalDate date2);

    List<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfterAndHalls(LocalDate date,
                                                                             LocalDate date2,
                                                                             List<HallForSeanceDTO> halls);

    Page<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfter(LocalDate date,
                                                                     LocalDate date2,
                                                                     Pageable pageable);

    Page<SeanceForSeancesListDTO> findByDateFromBeforeAndDateToAfterAndHalls(LocalDate date,
                                                                             LocalDate date2,
                                                                             List<HallForSeanceDTO> halls,
                                                                             Pageable pageable);

    Page<Map.Entry> findSeancesList(LocalDate currentDate, CinemaDTO cinema);

    void delete(SeanceSimpleDTO seance);

    void deleteAll();
}
