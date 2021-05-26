package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieForComingSoonDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private MPAA mpaaRating;

    private double usersRating;

    @NotNull
    private LocalDate releaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForComingSoonDTO that = (MovieForComingSoonDTO) o;
        return Double.compare(that.usersRating, usersRating) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(posterPicture, that.posterPicture) &&
                mpaaRating == that.mpaaRating &&
                Objects.equals(releaseDate, that.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, posterPicture, mpaaRating, usersRating, releaseDate);
    }
}
