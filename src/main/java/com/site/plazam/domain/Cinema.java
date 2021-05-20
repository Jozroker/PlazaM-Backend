package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "cinema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cinema {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @NotEmpty
    private Map<String, String> name = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> country = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> city = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> street = new HashMap<>();

    //todo self Cascade interface and its resolver

    public Cinema(@NotNull @NotEmpty Map<String, String> country,
                  @NotNull @NotEmpty Map<String, String> city,
                  @NotNull @NotEmpty Map<String, String> street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
