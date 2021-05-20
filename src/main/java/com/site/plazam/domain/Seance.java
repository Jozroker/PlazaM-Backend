package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "seance")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Seance {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @Field(name = "start_seance")
    private LocalTime startSeance;

    @NotNull
    @Field(name = "end_seance")
    private LocalTime endSeance;

    @NotNull
    @Field(name = "date_from")
    private LocalDate dateFrom;

    @NotNull
    @Field(name = "date_to")
    private LocalDate dateTo;

    @NotNull
    @Field(name = "ticket_price")
    private Float ticketPrice;

    @NotNull
    @Field(name = "hall_id")
    private String hallId;

    @NotNull
    @Field(name = "movie_id")
    private String movieId;

    @NotNull
    @NotEmpty
    private List<Day> days = new ArrayList<>();

    public Seance(@NotNull LocalTime startSeance,
                  @NotNull LocalTime endSeance,
                  @NotNull LocalDate dateFrom,
                  @NotNull LocalDate dateTo,
                  @NotNull Float ticketPrice,
                  @NotNull String hallId,
                  @NotNull String movieId,
                  @NotNull @NotEmpty List<Day> days) {
        this.startSeance = startSeance;
        this.endSeance = endSeance;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.ticketPrice = ticketPrice;
        this.hallId = hallId;
        this.movieId = movieId;
        this.days = days;
    }
}
