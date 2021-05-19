package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "cinema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cinema {

    @MongoId
    @Indexed(unique = true)
    private String id;

    @NotNull
    private Map<String, String> country = new HashMap<>();

    @NotNull
    private Map<String, String> city = new HashMap<>();

    @NotNull
    private Map<String, String> street = new HashMap<>();

    @Field(name = "halls_ids")
    @DBRef
    //todo self Cascade interface and its resolver
    private List<Hall> halls = new ArrayList<>();

    public Cinema(@NotNull Map<String, String> country,
                  @NotNull Map<String, String> city,
                  @NotNull Map<String, String> street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Cinema(@NotNull Map<String, String> country,
                  @NotNull Map<String, String> city,
                  @NotNull Map<String, String> street,
                  List<Hall> halls) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.halls = halls;
    }
}
