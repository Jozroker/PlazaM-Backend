package com.site.plazam.dto.parents;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PictureDTO {

    private String id;

    @NotNull
    @NotEmpty
    private byte[] picture = new byte[0];
}
