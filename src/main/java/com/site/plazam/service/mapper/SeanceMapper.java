package com.site.plazam.service.mapper;

import com.site.plazam.domain.Seance;
import com.site.plazam.dto.*;
import com.site.plazam.service.HallService;
import com.site.plazam.service.MovieService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class SeanceMapper {

    final MovieService ms;

    final HallService hs;

    protected SeanceMapper(MovieService movieService, HallService hallService) {
        this.ms = movieService;
        this.hs = hallService;
    }

    @Mapping(source = "movie", target = "movieId",
            qualifiedByName = "toMovieId")
    @Mapping(source = "hall.id", target = "hallId")
    public abstract Seance toEntity(SeanceCreateDTO seanceCreateDTO);

    @Mapping(source = "movieId", target = "movie", qualifiedByName = "toMovie")
    @Mapping(source = "hallId", target = "hall", qualifiedByName = "toHall")
    public abstract SeanceForTicketDTO toSeanceForTicketDTO(Seance seance);

    @Mapping(source = "movieId", target = "movie", qualifiedByName = "toMovie")
    @Mapping(source = "hallId", target = "hall", qualifiedByName = "toHall")
    public abstract SeanceForSeancesListDTO toSeanceForSeancesListDTO(Seance seance);

    String toMovieId(MovieForSeanceDTO movieForSeanceDTO) {
        if (movieForSeanceDTO == null) {
            return null;
        }
        return movieForSeanceDTO.getId();
    }

    HallForSeanceDTO toHall(String id) {
        return hs.findHallForSeanceById(id);
    }

    MovieForSeanceDTO toMovie(String id) {
        return ms.findMovieForSeanceById(id);
    }
}
