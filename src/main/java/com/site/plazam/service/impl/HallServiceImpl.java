package com.site.plazam.service.impl;

import com.site.plazam.domain.Technology;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.HallForTicketDTO;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;
import com.site.plazam.repository.HallRepository;
import com.site.plazam.service.HallService;
import com.site.plazam.service.SeanceService;
import com.site.plazam.service.mapper.HallMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hr;

    private final HallMapper hm;

    @Lazy
    private final SeanceService ss;

    public HallServiceImpl(HallRepository hallRepository,
                           HallMapper hallMapper,
                           @Lazy SeanceService seanceService) {
        this.hr = hallRepository;
        this.hm = hallMapper;
        this.ss = seanceService;
    }

    @Override
    public HallForSeanceDTO findHallForSeanceById(String id) {
        return hr.findById(id).map(hm::toHallForSeanceDTO).orElse(null);
    }

    @Override
    public List<HallForSeanceDTO> findHallForSeanceByCinema(CinemaDTO cinemaDTO) {
        return hr.findByCinemaId(cinemaDTO.getId()).stream().map(hm::toHallForSeanceDTO).collect(Collectors.toList());
    }

    @Override
    public List<HallForSeanceDTO> findHallForSeanceByTechnology(Technology technology) {
        return hr.findByTechnology(technology).stream().map(hm::toHallForSeanceDTO).collect(Collectors.toList());
    }

    @Override
    public List<HallForSeanceDTO> findHallForSeanceAll() {
        return hr.findAll().stream().map(hm::toHallForSeanceDTO).collect(Collectors.toList());
    }

    @Override
    public HallForTicketDTO findHallForTicketById(String id) {
        return hr.findById(id).map(hm::toHallForTicketDTO).orElse(null);
    }

    @Override
    public List<HallForTicketDTO> findHallForTicketByCinema(CinemaDTO cinemaDTO) {
        return hr.findByCinemaId(cinemaDTO.getId()).stream().map(hm::toHallForTicketDTO).collect(Collectors.toList());
    }

    @Override
    public List<HallForTicketDTO> findHallForTicketByTechnology(Technology technology) {
        return hr.findByTechnology(technology).stream().map(hm::toHallForTicketDTO).collect(Collectors.toList());
    }

    @Override
    public List<HallForTicketDTO> findHallForTicketAll() {
        return hr.findAll().stream().map(hm::toHallForTicketDTO).collect(Collectors.toList());
    }

    @Override
    public List<HallForSeanceDTO> findByTechnologyIsIn(List<Technology> technologies) {
        return hr.findByTechnologyIsIn(technologies).stream().map(hm::toHallForSeanceDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(HallSimpleDTO hall) {
        HallForSeanceDTO hallSearch = new HallForSeanceDTO();
        hall.setId(hall.getId());
        ss.findByHall(hallSearch).forEach(ss::delete);
        hr.deleteById(hall.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        ss.deleteAll();
        hr.deleteAll();
    }
}
