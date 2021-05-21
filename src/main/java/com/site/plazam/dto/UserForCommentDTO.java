package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForCommentDTO extends UserSimpleDTO {

    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private byte[] picture = new byte[0];

    @NotNull
    private Country homeCountry;
}
