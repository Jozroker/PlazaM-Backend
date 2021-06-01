package com.site.plazam.dto;

import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserForRegistrationDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String registerPassword;

    @NotNull
    private String confirmPassword;

    private CinemaDTO selectedCinema;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForRegistrationDTO that = (UserForRegistrationDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(registerPassword, that.registerPassword) &&
                Objects.equals(confirmPassword, that.confirmPassword) &&
                Objects.equals(selectedCinema, that.selectedCinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, firstName, lastName, email, registerPassword, confirmPassword, selectedCinema);
    }
}
