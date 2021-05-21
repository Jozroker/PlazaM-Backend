package com.site.plazam.dto.parents;

import com.site.plazam.dto.SeanceForTicketDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketSimpleDTO {

    private String id;

    @NotNull
    private LocalDate date;

    @NotNull
    private int placeRow;

    @NotNull
    private int placeSeat;

    private float placeAllowance;

    @NotNull
    private SeanceForTicketDTO seance;
}
