package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Document(collection = "hall")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Hall {

    @MongoId
    @Indexed(unique = true)
    private String id;

    @NotNull
    private Integer rows;

    @NotNull
    private Integer columns;

    @NotNull
    private Technology technology;

    @Field(name = "cinema_id")
    @NotNull
    @DBRef
    private Cinema cinema;

    public Hall(@NotNull Integer rows,
                @NotNull Integer columns,
                @NotNull Technology technology) {
        this.rows = rows;
        this.columns = columns;
        this.technology = technology;
    }

    public Hall(@NotNull Integer rows,
                @NotNull Integer columns,
                @NotNull Technology technology,
                @NotNull Cinema cinema) {
        this.rows = rows;
        this.columns = columns;
        this.technology = technology;
        this.cinema = cinema;
    }
}
