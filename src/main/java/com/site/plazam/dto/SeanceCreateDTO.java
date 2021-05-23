package com.site.plazam.dto;

import com.site.plazam.dto.parents.SeanceSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeanceCreateDTO extends SeanceSimpleDTO {

    @NotNull
    private String movieName;

    private MovieForResultListDTO movie;

    @NotNull
    private HallForTicketDTO hall;

    @NotNull
    private float ticketPrice = 0F;

}
