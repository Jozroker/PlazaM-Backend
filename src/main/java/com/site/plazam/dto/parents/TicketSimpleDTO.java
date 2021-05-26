package com.site.plazam.dto.parents;

import com.site.plazam.dto.SeanceForTicketDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

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

    private double placeAllowance;

    @NotNull
    private SeanceForTicketDTO seance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketSimpleDTO that = (TicketSimpleDTO) o;
        return placeRow == that.placeRow &&
                placeSeat == that.placeSeat &&
                Double.compare(that.placeAllowance, placeAllowance) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(seance, that.seance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, placeRow, placeSeat, placeAllowance, seance);
    }
}
