package com.site.plazam.dto;

import com.site.plazam.dto.parents.SeanceSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class SeanceForSeancesListDTO extends SeanceSimpleDTO {

    @NotNull
    private MovieForSeanceDTO movie;

    @NotNull
    private HallForSeanceDTO hall;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SeanceForSeancesListDTO that = (SeanceForSeancesListDTO) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(hall, that.hall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), movie, hall);
    }
}
