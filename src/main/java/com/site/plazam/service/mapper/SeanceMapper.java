package com.site.plazam.service.mapper;

import com.site.plazam.domain.Seance;
import com.site.plazam.dto.*;
import com.site.plazam.service.HallService;
import com.site.plazam.service.MovieService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SeanceMapper {

    @Autowired
    MovieService ms;

    @Autowired
    HallService hs;

    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "hall.id", target = "hallId")
    public abstract Seance toEntity(SeanceCreateDTO seanceCreateDTO);

    @Mapping(source = "movieId", target = "movie", qualifiedByName =
            "toMovieForTicket")
    @Mapping(source = "hallId", target = "hall", qualifiedByName =
            "toHallForTicket")
//    @Mapping(target = "movie", expression = "java(toMovie(seance.getMovieId()))")
//    @Mapping(target = "hall", expression = "java(toHall(seance.getHallId()))")
    public abstract SeanceForTicketDTO toSeanceForTicketDTO(Seance seance);

    @Mapping(source = "movieId", target = "movie", qualifiedByName =
            "toMovieForSeance")
    @Mapping(source = "hallId", target = "hall", qualifiedByName =
            "toHallForSeance")
//    @Mapping(target = "movie", expression = "java(toMovie(seance.getMovieId()))")
//    @Mapping(target = "hall", expression = "java(toHall(seance.getHallId()))")
    public abstract SeanceForSeancesListDTO toSeanceForSeancesListDTO(Seance seance);

    @Named("toHallForSeance")
    HallForSeanceDTO toHallForSeance(String id) {
        return hs.findHallForSeanceById(id);
    }

    @Named("toHallForTicket")
    HallForTicketDTO toHallForTicket(String id) {
        return hs.findHallForTicketById(id);
    }

    @Named("toMovieForSeance")
    MovieForSeanceDTO toMovieForSeance(String id) {
        return ms.findMovieForSeanceById(id);
    }

    @Named("toMovieForTicket")
    MovieForTicketDTO toMovieForTicket(String id) {
        return ms.findMovieForTicketById(id);
    }
}
