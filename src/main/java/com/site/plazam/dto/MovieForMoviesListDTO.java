package com.site.plazam.dto;

import com.site.plazam.domain.Technology;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForMoviesListDTO extends MovieSimpleDTO {

    @NotNull
    @NotEmpty
    private byte[] posterPicture = new byte[0];

    @NotNull
    private int durationInMinutes;

    @NotNull
    private float imdbRating;

    private List<Technology> availableTechnologies;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private float usersRating = 0F;

}
