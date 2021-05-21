package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForBannedListDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    @NotEmpty
    private byte[] picture = new byte[0];

    @NotNull
    private LocalDate bannedTo;

    @NotNull
    private Country country;
}
