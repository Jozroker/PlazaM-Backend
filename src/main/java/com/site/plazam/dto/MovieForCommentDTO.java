package com.site.plazam.dto;

import com.site.plazam.domain.MPAA;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class MovieForCommentDTO extends MovieForHomeSliderDTO {

    @NotNull
    private MPAA mpaaRating;

    private double usersRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieForCommentDTO that = (MovieForCommentDTO) o;
        return Double.compare(that.usersRating, usersRating) == 0 &&
                mpaaRating == that.mpaaRating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mpaaRating, usersRating);
    }
}
