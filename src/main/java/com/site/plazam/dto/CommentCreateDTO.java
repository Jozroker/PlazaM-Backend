package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentCreateDTO extends CommentSimpleDTO {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private UserForSelfInfoDTO user;

    @NotNull
    private MovieFullDTO movie;

    private RatingSimpleDTO rating;
}
