package com.site.plazam.service;

import com.site.plazam.dto.parents.CinemaDTO;

import java.util.List;

public interface CinemaService {

//    CinemaDTO save(CinemaDTO cinemaDTO);

    CinemaDTO findById(String id);

    List<CinemaDTO> findAll();

    void delete(CinemaDTO cinema);

    void deleteAll();
}
