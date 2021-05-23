package com.site.plazam.service.impl;

import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.repository.CinemaRepository;
import com.site.plazam.service.CinemaService;
import com.site.plazam.service.mapper.CinemaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cr;

    private final CinemaMapper cm;

    public CinemaServiceImpl(CinemaRepository cinemaRepository,
                             CinemaMapper cinemaMapper) {
        this.cr = cinemaRepository;
        this.cm = cinemaMapper;
    }

//    @Override
//    public CinemaDTO save(CinemaDTO cinemaDTO) {
//        return cm.toDTO(cr.save(cm.to(pictureDTO)));
//    }

    @Override
    public CinemaDTO findById(String id) {
        return cr.findById(id).map(cm::toDTO).orElse(null);
    }

    @Override
    public List<CinemaDTO> findAll() {
        return cr.findAll().stream().map(cm::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(CinemaDTO cinema) {
        cr.deleteById(cinema.getId());
    }

    @Override
    public void deleteAll() {
        cr.deleteAll();
    }
}
