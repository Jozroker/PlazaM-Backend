package com.site.plazam.dto.parents;

import com.site.plazam.domain.Day;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeanceSimpleDTO {

    private String id;

    @NotNull
    private LocalDateTime startSeance;

    private LocalDateTime endSeance;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;

    @NotNull
    @NotEmpty
    private List<Day> days = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeanceSimpleDTO that = (SeanceSimpleDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(startSeance, that.startSeance) &&
                Objects.equals(endSeance, that.endSeance) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startSeance, endSeance, dateFrom, dateTo, days);
    }
}
