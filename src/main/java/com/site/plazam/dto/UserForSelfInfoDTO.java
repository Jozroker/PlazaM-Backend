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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserForSelfInfoDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    private String firstName;

    private String lastName;

    @NotNull
    private String email;

    private String phone;

    private PictureDTO picture;

    private Country country;

    private String homeCity;

    private Sex sex;

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
    private boolean useRealName;

    private List<TicketSimpleDTO> tickets = new ArrayList<>();

    private Lang selectedLang;

    private CinemaDTO selectedCinema;

    private List<MessageForUserDTO> messages = new ArrayList<>();

    private List<String> favouriteMovieIds = new ArrayList<>();

    private List<String> viewedMovieIds = new ArrayList<>();

    private List<String> waitMovieIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForSelfInfoDTO that = (UserForSelfInfoDTO) o;
        return phoneConfirmed == that.phoneConfirmed &&
                emailConfirmed == that.emailConfirmed &&
                useLightTheme == that.useLightTheme &&
                hide18PlusMovies == that.hide18PlusMovies &&
                useRealName == that.useRealName &&
                Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(picture, that.picture) &&
                country == that.country &&
                Objects.equals(homeCity, that.homeCity) &&
                sex == that.sex &&
                Objects.equals(aboutMe, that.aboutMe) &&
                Objects.equals(tickets, that.tickets) &&
                selectedLang == that.selectedLang &&
                Objects.equals(selectedCinema, that.selectedCinema) &&
                Objects.equals(messages, that.messages) &&
                Objects.equals(favouriteMovieIds, that.favouriteMovieIds) &&
                Objects.equals(viewedMovieIds, that.viewedMovieIds) &&
                Objects.equals(waitMovieIds, that.waitMovieIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, firstName, lastName, email, phone, picture, country, homeCity, sex, aboutMe, phoneConfirmed, emailConfirmed, useLightTheme, hide18PlusMovies, useRealName, tickets, selectedLang, selectedCinema, messages, favouriteMovieIds, viewedMovieIds, waitMovieIds);
    }
}
