package com.site.plazam.service.impl;

import com.site.plazam.dto.MovieFullDTO;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import com.site.plazam.repository.RatingRepository;
import com.site.plazam.service.RatingService;
import com.site.plazam.service.mapper.RatingMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository rr;

    private final RatingMapper rm;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.rr = ratingRepository;
        this.rm = ratingMapper;
    }

    @Override
    public RatingSimpleDTO save(RatingCreateDTO ratingCreateDTO) {
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
    public RatingSimpleDTO findByUserAndByMovie(UserForSelfInfoDTO userForSelfInfoDTO, MovieFullDTO movieFullDTO) {
        return rr.findByUserIdAndMovieId(userForSelfInfoDTO.getId(), movieFullDTO.getId())
                .map(rm::toDTO).orElse(null);
    }

    @Override
    public void delete(RatingSimpleDTO rating) {
        rr.deleteById(rating.getId());
    }

    @Override
    public void deleteAll() {
        rr.deleteAll();
    }
}
