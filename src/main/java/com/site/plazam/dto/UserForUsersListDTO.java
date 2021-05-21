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
public class UserForUsersListDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    @NotEmpty
    private byte[] picture = new byte[0];

    @NotNull
    private boolean banned;

    @NotNull
    private Country country;
}
