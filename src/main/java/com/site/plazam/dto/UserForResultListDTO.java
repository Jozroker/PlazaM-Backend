package com.site.plazam.dto;

import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForResultListDTO extends UserSimpleDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
