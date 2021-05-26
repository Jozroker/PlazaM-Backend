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
@ToString(callSuper = true)
public class MovieForSeanceDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private int durationInMinutes;

    private double usersRating;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private double imdbRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForSeanceDTO that = (MovieForSeanceDTO) o;
        return durationInMinutes == that.durationInMinutes &&
                Double.compare(that.usersRating, usersRating) == 0 &&
                Double.compare(that.imdbRating, imdbRating) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(posterPicture, that.posterPicture) &&
                mpaaRating == that.mpaaRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, posterPicture, durationInMinutes, usersRating, mpaaRating, imdbRating);
    }
}
