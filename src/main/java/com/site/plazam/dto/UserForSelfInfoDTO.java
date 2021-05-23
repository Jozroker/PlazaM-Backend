package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.domain.Lang;
import com.site.plazam.domain.Sex;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

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
    private PictureDTO picture;

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
    private CinemaDTO selectedCinema;

    private List<MessageForUserDTO> messages;

    private List<MovieForMoviesListDTO> favouriteMovies;

    private List<MovieForMoviesListDTO> viewedMovies;

    private List<MovieForMoviesListDTO> waitMovies;
    //todo fix this lists to less-data
}
