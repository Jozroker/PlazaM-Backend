package com.site.plazam.dto.parents;

import com.site.plazam.domain.Country;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CinemaDTO {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Country country;

    @NotNull
    private String city;

    @NotNull
    private String street;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinemaDTO cinemaDTO = (CinemaDTO) o;
        return Objects.equals(id, cinemaDTO.id) &&
                Objects.equals(name, cinemaDTO.name) &&
                country == cinemaDTO.country &&
                Objects.equals(city, cinemaDTO.city) &&
                Objects.equals(street, cinemaDTO.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, city, street);
    }
}
