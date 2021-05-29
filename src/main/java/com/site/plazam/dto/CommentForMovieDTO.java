package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
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
public class CommentForMovieDTO extends CommentSimpleDTO {

    @NotNull
    private UserForCommentDTO user;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommentForMovieDTO that = (CommentForMovieDTO) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, date, time);
    }
}
