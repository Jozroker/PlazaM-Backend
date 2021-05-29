package com.site.plazam.service;

import com.site.plazam.dto.MovieCreateDTO;
import com.site.plazam.dto.MovieFullDTO;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;

import java.util.List;

public interface RatingService {

    RatingSimpleDTO save(RatingCreateDTO ratingCreateDTO);

    RatingSimpleDTO findById(String id);

    List<RatingSimpleDTO> findAll();

    List<RatingSimpleDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO);

    List<RatingSimpleDTO> findByMovie(MovieFullDTO movieFullDTO);

    RatingSimpleDTO findByUserAndMovie(UserForSelfInfoDTO userForSelfInfoDTO, MovieFullDTO movieFullDTO);

    List<MovieCreateDTO> findRatedMoviesByUser(UserSimpleDTO user);

    void delete(RatingSimpleDTO rating);

    void deleteByUser(UserSimpleDTO user);

    void deleteByMovie(MovieSimpleDTO movie);

    void deleteAll();
}
