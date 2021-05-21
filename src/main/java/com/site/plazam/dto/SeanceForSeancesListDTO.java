package com.site.plazam.dto;

import com.site.plazam.dto.parents.SeanceSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeanceForSeancesListDTO extends SeanceSimpleDTO {

    @NotNull
    private MovieForSeanceDTO movie;

    @NotNull
    private HallForSeanceDTO hall;

}
