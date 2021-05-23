package com.site.plazam.dto;

import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForResultListDTO extends MovieSimpleDTO {

    @NotNull
    private String fullName;

}
