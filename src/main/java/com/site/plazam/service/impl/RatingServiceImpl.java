package com.site.plazam.service.impl;

import com.site.plazam.dto.MovieCreateDTO;
import com.site.plazam.dto.MovieFullDTO;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import com.site.plazam.repository.RatingRepository;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.RatingService;
import com.site.plazam.service.mapper.RatingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository rr;

    private final RatingMapper rm;

    private final MovieService ms;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             RatingMapper ratingMapper,
                             MovieService movieService) {
        this.rr = ratingRepository;
        this.rm = ratingMapper;
        this.ms = movieService;
    }

    @Override
    @Transactional
    public RatingSimpleDTO save(RatingCreateDTO ratingCreateDTO) {
        MovieCreateDTO movieCreateDTO =
                ms.findMovieCreateById(ratingCreateDTO.getMovie().getId());
        List<RatingSimpleDTO> ratings =
                findByMovie(ratingCreateDTO.getMovie());
        double newRating =
                Math.round(((ratings.stream().mapToDouble(RatingSimpleDTO::getUserRating)
                        .sum() + ratingCreateDTO.getUserRating()) / (ratings.size() + 1)) * 10) / 10.0;
        movieCreateDTO.setUsersRating(newRating);
        ms.save(movieCreateDTO);
        return rm.toDTO(rr.save(rm.toEntity(ratingCreateDTO)));
    }

    @Override
    public RatingSimpleDTO findById(String id) {
        return rr.findById(id).map(rm::toDTO).orElse(null);
    }

    @Override
    public List<RatingSimpleDTO> findAll() {
        return rr.findAll().stream().map(rm::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RatingSimpleDTO> findByUser(UserForSelfInfoDTO userForSelfInfoDTO) {
        return rr.findByUserId(userForSelfInfoDTO.getId()).stream().map(rm::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<RatingSimpleDTO> findByMovie(MovieFullDTO movieFullDTO) {
        return rr.findByMovieId(movieFullDTO.getId()).stream().map(rm::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<MovieCreateDTO> findRatedMoviesByUser(UserSimpleDTO user) {
        return rr.findByUserId(user.getId()).stream().map(rating ->
                ms.findMovieCreateById(rating.getMovieId())).distinct()
                .collect(Collectors.toList());
    }

    @Override
    public RatingSimpleDTO findByUserAndMovie(UserForSelfInfoDTO userForSelfInfoDTO, MovieFullDTO movieFullDTO) {
        return rr.findByUserIdAndMovieId(userForSelfInfoDTO.getId(), movieFullDTO.getId())
                .map(rm::toDTO).orElse(null);
    }

    @Override
    public void delete(RatingSimpleDTO rating) {
        rr.deleteById(rating.getId());
    }

    @Override
    @Transactional
    public void deleteByUser(UserSimpleDTO user) {
        List<MovieCreateDTO> movies = findRatedMoviesByUser(user);
        rr.deleteByUserId(user.getId());
        movies.forEach(movie -> {
            List<RatingSimpleDTO> ratings =
                    findByMovie(ms.findMovieFullById(movie.getId()));
            double newRating =
                    Math.round((ratings.stream().mapToDouble(RatingSimpleDTO::getUserRating)
                            .sum() / ratings.size()) * 10) / 10.0;
            movie.setUsersRating(newRating);
            ms.save(movie);
        });
    }

    @Override
    public void deleteByMovie(MovieSimpleDTO movie) {
        rr.deleteByMovieId(movie.getId());
    }

    @Override
    public void deleteAll() {
        rr.deleteAll();
    }
}
