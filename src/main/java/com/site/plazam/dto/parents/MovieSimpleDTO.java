package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieSimpleDTO {

    private String id;

    @NotNull
    private String name;

    private String surname;
}
