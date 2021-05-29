package com.site.plazam.service.impl;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.repository.CinemaRepository;
import com.site.plazam.service.CinemaService;
import com.site.plazam.service.HallService;
import com.site.plazam.service.mapper.CinemaMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cr;

    private final CinemaMapper cm;

    @Lazy
    private final HallService hs;

    public CinemaServiceImpl(CinemaRepository cinemaRepository,
                             CinemaMapper cinemaMapper,
                             @Lazy HallService hallService) {
        this.cr = cinemaRepository;
        this.cm = cinemaMapper;
        this.hs = hallService;
    }

//    @Override
//    public CinemaDTO save(CinemaDTO cinemaDTO) {
//        return cm.toDTO(cr.save(cm.to(pictureDTO)));
//    }


    @Override
    public CinemaDTO findFirstByCountryAndCity(Country country,
                                               String city) {
        return cr.findFirstByCountryAndCity(country, city).stream().findFirst().map(cm::toDTO).orElse(null);
    }

    @Override
    public CinemaDTO findById(String id) {
        return cr.findById(id).map(cm::toDTO).orElse(null);
    }

    @Override
    public List<CinemaDTO> findAll() {
        return cr.findAll().stream().map(cm::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(CinemaDTO cinema) {
        hs.findHallForSeanceByCinema(cinema).forEach(hs::delete);
        cr.deleteById(cinema.getId());
    }

    @Override
    @Transactional
    public void deleteAll() {
        hs.deleteAll();
        cr.deleteAll();
    }
}
