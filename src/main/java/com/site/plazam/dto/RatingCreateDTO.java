package com.site.plazam.dto;

import com.site.plazam.dto.parents.RatingSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class RatingCreateDTO extends RatingSimpleDTO {

    @NotNull
    private UserForSelfInfoDTO user;

    @NotNull
    private MovieFullDTO movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RatingCreateDTO that = (RatingCreateDTO) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(movie, that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, movie);
    }
}
