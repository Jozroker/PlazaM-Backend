package com.site.plazam.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallForTicketDTO extends HallForSeanceDTO {

    @NotNull
    private int rows;

    @NotNull
    private int columns;

}
