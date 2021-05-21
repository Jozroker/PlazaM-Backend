package com.site.plazam.dto;

import com.site.plazam.dto.parents.SeanceSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeanceForTicketDTO extends SeanceSimpleDTO {

    @NotNull
    private MovieForTicketDTO movie;

    @NotNull
    private HallForTicketDTO hall;

    @NotNull
    private float ticketPrice = 0F;

}
