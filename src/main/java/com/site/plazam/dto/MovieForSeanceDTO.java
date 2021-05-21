package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForSeanceDTO extends MovieSimpleDTO {

    @NotNull
    @NotEmpty
    private byte[] posterPicture = new byte[0];

    @NotNull
    private int durationInMinutes;

    private float usersRating;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private float imdbRating;

}
