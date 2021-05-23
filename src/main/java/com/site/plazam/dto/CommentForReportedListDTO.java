package com.site.plazam.dto;

import com.site.plazam.dto.parents.CommentSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentForReportedListDTO extends CommentSimpleDTO {

    @NotNull
    private UserForReportedListDTO user;
}
