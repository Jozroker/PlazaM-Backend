package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class CommentCreateDTO extends CommentSimpleDTO {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private UserForSelfInfoDTO user;

    @NotNull
    private MovieFullDTO movie;

    private RatingSimpleDTO rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommentCreateDTO that = (CommentCreateDTO) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(user, that.user) &&
                Objects.equals(movie, that.movie) &&
                Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, time, user, movie, rating);
    }
}
