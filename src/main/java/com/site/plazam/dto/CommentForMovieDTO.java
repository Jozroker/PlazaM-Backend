package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentForMovieDTO extends CommentSimpleDTO {

    @NotNull
    private UserForCommentDTO user;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;
}
