package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.domain.Lang;
import com.site.plazam.domain.Sex;
import com.site.plazam.dto.parents.CinemaSimpleDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForSelfInfoDTO extends UserSimpleDTO {

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
    private Country country;

    @NotNull
    private String homeCity;

    @NotNull
    private Sex sex;

    @NotNull
    private String aboutMe;

    @NotNull
    private boolean phoneConfirmed;

    @NotNull
    private boolean emailConfirmed;

    @NotNull
    private boolean useLightTheme;

    @NotNull
    private boolean hide18PlusMovies;

    @NotNull
    private boolean banned;

    @NotNull
    private boolean useRealName;

    private List<TicketSimpleDTO> tickets;

    @NotNull
    private Lang selectedLang;

    @NotNull
    private CinemaSimpleDTO selectedCinema;

    private List<MessageDTO> messages;
}
