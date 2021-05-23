package com.site.plazam.service.mapper;

import com.site.plazam.domain.Rating;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RatingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "movie.id", target = "movieId")
    Rating toEntity(RatingCreateDTO ratingCreateDTO);

    RatingSimpleDTO toDTO(Rating rating);
}
