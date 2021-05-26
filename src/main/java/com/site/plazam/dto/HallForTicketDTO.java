package com.site.plazam.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class HallForTicketDTO extends HallForSeanceDTO {

    @NotNull
    private int rows;

    @NotNull
    private int columns;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HallForTicketDTO that = (HallForTicketDTO) o;
        return rows == that.rows &&
                columns == that.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rows, columns);
    }
}
