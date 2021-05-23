package com.site.plazam.dto;

import com.site.plazam.dto.parents.RatingSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RatingCreateDTO extends RatingSimpleDTO {

    @NotNull
    private UserForSelfInfoDTO user;

    @NotNull
    private MovieFullDTO movie;
}
