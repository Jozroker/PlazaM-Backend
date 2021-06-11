package com.site.plazam.dto;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.Technology;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieForMoviesListDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private int durationInMinutes;

    @NotNull
    private double imdbRating;

    private Set<Technology> availableTechnologies = new HashSet<>();

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private double usersRating = 0;

    @NotNull
    @NotEmpty
    private List<Genre> genres = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForMoviesListDTO that = (MovieForMoviesListDTO) o;
        return durationInMinutes == that.durationInMinutes && Double.compare(that.imdbRating, imdbRating) == 0 && Double.compare(that.usersRating, usersRating) == 0 && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(posterPicture, that.posterPicture) && Objects.equals(availableTechnologies, that.availableTechnologies) && Objects.equals(releaseDate, that.releaseDate) && Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, posterPicture, durationInMinutes, imdbRating, availableTechnologies, releaseDate, usersRating, genres);
    }
}
