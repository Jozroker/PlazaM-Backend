package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForComingSoonDTO extends MovieSimpleDTO {

    @NotNull
    @NotEmpty
    private byte[] posterPicture = new byte[0];

    @NotNull
    private MPAA mpaaRating;

    private float usersRating;

    @NotNull
    private LocalDate releaseDate;

}
