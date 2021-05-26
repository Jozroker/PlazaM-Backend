package com.site.plazam.service;

import com.site.plazam.domain.Technology;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.HallForTicketDTO;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;

import java.util.List;

public interface HallService {

    HallForSeanceDTO findHallForSeanceById(String id);

    List<HallForSeanceDTO> findHallForSeanceByCinema(CinemaDTO cinemaDTO);

    List<HallForSeanceDTO> findHallForSeanceByTechnology(Technology technology);

    List<HallForSeanceDTO> findHallForSeanceAll();

    HallForTicketDTO findHallForTicketById(String id);

    List<HallForTicketDTO> findHallForTicketByCinema(CinemaDTO cinemaDTO);

    List<HallForTicketDTO> findHallForTicketByTechnology(Technology technology);

    List<HallForTicketDTO> findHallForTicketAll();

    List<HallForSeanceDTO> findByTechnologyIsIn(List<Technology> technologies);

    void delete(HallSimpleDTO hall);

    void deleteAll();
}
