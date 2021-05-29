package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
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
    private PictureDTO picture;

    @NotNull
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForUsersListDTO that = (UserForUsersListDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(picture, that.picture) &&
                country == that.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, firstName, lastName, email, phone, picture, country);
    }
}
