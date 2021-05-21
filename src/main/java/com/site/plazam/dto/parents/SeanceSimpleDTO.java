package com.site.plazam.dto.parents;

import com.site.plazam.domain.Day;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeanceSimpleDTO {

    private String id;

    @NotNull
    private LocalTime startSeance;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private LocalDate dateTo;

    @NotNull
    @NotEmpty
    private List<Day> days = new ArrayList<>();
}
