package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RatingSimpleDTO {

    private String id;

    @NotNull
    private float userRating;
}
