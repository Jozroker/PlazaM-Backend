package com.site.plazam.service.impl;

import com.site.plazam.domain.Technology;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.HallForTicketDTO;
import com.site.plazam.dto.parents.HallSimpleDTO;
import com.site.plazam.repository.HallRepository;
import com.site.plazam.service.HallService;
import com.site.plazam.service.mapper.HallMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hr;

    private final HallMapper hm;

    public HallServiceImpl(HallRepository hallRepository, HallMapper hallMapper) {
        this.hr = hallRepository;
        this.hm = hallMapper;
    }

    @Override
    public HallForSeanceDTO findHallForSeanceById(String id) {
        return hr.findById(id).map(hm::toHallForSeanceDTO).orElse(null);
    }

    @Override
    public List<HallForSeanceDTO> findHallForSeanceByCinemaId(String id) {
        return hr.findByCinemaId(id).stream().map(hm::toHallForSeanceDTO).collect(Collectors.toList());
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
    public List<HallForTicketDTO> findHallForTicketByCinemaId(String id) {
        return hr.findByCinemaId(id).stream().map(hm::toHallForTicketDTO).collect(Collectors.toList());
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
    public void delete(HallSimpleDTO hall) {
        hr.deleteById(hall.getId());
    }

    @Override
    public void deleteAll() {
        hr.deleteAll();
    }
}
