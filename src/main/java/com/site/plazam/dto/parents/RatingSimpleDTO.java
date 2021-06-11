package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RatingSimpleDTO {

    private String id;

    @NotNull
    private int userRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingSimpleDTO that = (RatingSimpleDTO) o;
        return userRating == that.userRating && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userRating);
    }
}
