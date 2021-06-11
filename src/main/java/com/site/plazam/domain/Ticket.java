package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection = "ticket")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Field(name = "place_row")
    private Integer placeRow;

    @NotNull
    @Field(name = "place_seat")
    private Integer placeSeat;

    @Field(name = "place_allowance")
    private Double placeAllowance = (double) 0;

    @NotNull
    @Field(name = "seance_id")
    private String seanceId;

    @NotNull
    @Field(name = "payment_status")
    private Boolean paymentStatus = false;

    //todo add payed field
    //todo create receipt entity end liquidpay api

    public Ticket(@NotNull LocalDate date,
                  @NotNull Integer placeRow,
                  @NotNull Integer placeSeat,
                  @NotNull String seanceId) {
        this.date = date;
        this.placeRow = placeRow;
        this.placeSeat = placeSeat;
        this.seanceId = seanceId;
    }

    public Ticket(@NotNull LocalDate date,
                  @NotNull Integer placeRow,
                  @NotNull Integer placeSeat,
                  Double placeAllowance,
                  @NotNull String seanceId) {
        this.date = date;
        this.placeRow = placeRow;
        this.placeSeat = placeSeat;
        this.placeAllowance = placeAllowance;
        this.seanceId = seanceId;
    }
}
