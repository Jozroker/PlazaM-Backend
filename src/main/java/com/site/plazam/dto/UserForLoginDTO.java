package com.site.plazam.dto;

import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForLoginDTO extends UserSimpleDTO {

    @NotNull
    private String emailOrUsername;

    @NotNull
    private String password;
}
