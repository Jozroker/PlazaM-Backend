package com.site.plazam.dto;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieFullDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private PictureDTO widePicture;

    @NotNull
    private String description;

    @NotNull
    private int durationInMinutes;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private double usersRating = 0;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private double imdbRating = 0;

    @NotNull
    private String directedBy;

    @NotNull
    private String movieLang;

    @NotNull
    private String movieCountry;

    private List<PictureDTO> galleryPictures = new ArrayList<>();

    private List<ActorForActorListDTO> actors = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<Genre> genres = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieFullDTO that = (MovieFullDTO) o;
        return durationInMinutes == that.durationInMinutes &&
                Double.compare(that.usersRating, usersRating) == 0 &&
                Double.compare(that.imdbRating, imdbRating) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(posterPicture, that.posterPicture) &&
                Objects.equals(widePicture, that.widePicture) &&
                Objects.equals(description, that.description) &&
                Objects.equals(releaseDate, that.releaseDate) &&
                mpaaRating == that.mpaaRating &&
                Objects.equals(directedBy, that.directedBy) &&
                Objects.equals(movieLang, that.movieLang) &&
                Objects.equals(movieCountry, that.movieCountry) &&
                Objects.equals(galleryPictures, that.galleryPictures) &&
                Objects.equals(actors, that.actors) &&
                Objects.equals(genres, that.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, posterPicture, widePicture, description, durationInMinutes, releaseDate, usersRating, mpaaRating, imdbRating, directedBy, movieLang, movieCountry, galleryPictures, actors, genres);
    }
}
