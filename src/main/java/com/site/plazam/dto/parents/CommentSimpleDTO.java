package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentSimpleDTO {

    private String id;

    @NotNull
    private String text;

    private boolean reported;

}
