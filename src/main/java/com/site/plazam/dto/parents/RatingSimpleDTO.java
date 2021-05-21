package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RatingSimpleDTO {

    @NotNull
    private float userRating;
}
