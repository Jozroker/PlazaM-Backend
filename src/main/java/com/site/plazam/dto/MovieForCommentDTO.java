package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForCommentDTO extends MovieForHomeSliderDTO {

    @NotNull
    private MPAA mpaaRating;

    private float usersRating;

}
