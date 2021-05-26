package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class CommentForCommentsListDTO extends CommentSimpleDTO {

    @NotNull
    private MovieForCommentDTO movie;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommentForCommentsListDTO that = (CommentForCommentsListDTO) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(date, that.date) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), movie, date, time);
    }
}
