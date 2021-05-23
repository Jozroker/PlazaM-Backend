package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForSeanceDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private int durationInMinutes;

    private float usersRating;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private float imdbRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieForSeanceDTO that = (MovieForSeanceDTO) o;
        return durationInMinutes == that.durationInMinutes &&
                Float.compare(that.usersRating, usersRating) == 0 &&
                Float.compare(that.imdbRating, imdbRating) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(posterPicture, that.posterPicture) &&
                mpaaRating == that.mpaaRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, posterPicture, durationInMinutes, usersRating, mpaaRating, imdbRating);
    }
}
