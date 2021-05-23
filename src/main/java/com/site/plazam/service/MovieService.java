package com.site.plazam.service;

import com.site.plazam.domain.Genre;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {

    MovieFullDTO save(MovieCreateDTO movieCreateDTO);

    MovieForComingSoonDTO findMovieForComingSoonById(String id);

    MovieForCommentDTO findMovieForCommentById(String id);

    MovieForHomeSliderDTO findMovieForHomeSliderById(String id);

    MovieForMoviesListDTO findMovieForMoviesListById(String id);

    MovieForSeanceDTO findMovieForSeanceById(String id);

    MovieForTicketDTO findMovieForTicketById(String id);

    MovieFullDTO findMovieFullById(String id);


    List<MovieForComingSoonDTO> findMovieForComingSoonAll();

    List<MovieForHomeSliderDTO> findMovieForHomeSliderAll();

    List<MovieForMoviesListDTO> findMovieForMoviesListAll();

    Page<MovieForMoviesListDTO> findMovieForMoviesListAll(Pageable pageable);

    List<MovieForSeanceDTO> findMovieForSeanceByGenresIsContaining(List<Genre> genres);

    Page<MovieForSeanceDTO> findMovieForSeanceByGenresIsContaining(List<Genre> genres, Pageable pageable);


    List<MovieForMoviesListDTO> findMovieForMoviesListByMovieName(String fullName);

    List<MovieForResultListDTO> findMovieForResultListByMovieName(String fullName);

    Page<MovieForMoviesListDTO> findMovieForMoviesListByMovieName(String fullName,
                                                                  Pageable pageable);

    List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateAfter(LocalDate date);

    List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBefore(LocalDate date);

    List<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBetween(LocalDate from,
                                                                           LocalDate to);

    Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateAfter(LocalDate date,
                                                                         Pageable pageable);

    Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBefore(LocalDate date,
                                                                          Pageable pageable);

    Page<MovieForMoviesListDTO> findMovieForMoviesListByReleaseDateBetween(LocalDate from,
                                                                           LocalDate to,
                                                                           Pageable pageable);


    List<MovieForHomeSliderDTO> findMovieForHomeSlideByReleaseDateAfter(LocalDate date);

    List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateAfter(LocalDate date);

    List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateBefore(LocalDate date);

    List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateBetween(LocalDate from,
                                                                           LocalDate to);

    List<MovieForComingSoonDTO> findMovieForComingSoonByReleaseDateAfterOrderByReleaseDate(LocalDate date);

//    List<MovieForHomeSliderDTO> findMovieForHomeSliderByLastSeances(LocalDate currentDate);

//    Page<MovieForMoviesListDTO> findMovieForMoviesListByUserFavourites()

    void delete(MovieSimpleDTO movie);

    void deleteAll();
}
