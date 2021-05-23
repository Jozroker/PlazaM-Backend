package com.site.plazam.dto.parents;

import com.site.plazam.domain.Country;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CinemaDTO {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Country country;

    @NotNull
    private String city;

    @NotNull
    private String street;
}
