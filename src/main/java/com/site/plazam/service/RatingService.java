package com.site.plazam.service;

import com.site.plazam.dto.MovieFullDTO;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;

import java.util.List;

public interface RatingService {

    RatingSimpleDTO save(RatingCreateDTO ratingCreateDTO);

    RatingSimpleDTO findById(String id);

    List<RatingSimpleDTO> findAll();

    List<RatingSimpleDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO);

    List<RatingSimpleDTO> findByMovie(MovieFullDTO movieFullDTO);

    RatingSimpleDTO findByUserAndByMovie(UserForSelfInfoDTO userForSelfInfoDTO, MovieFullDTO movieFullDTO);

    void delete(RatingSimpleDTO rating);

    void deleteAll();
}
