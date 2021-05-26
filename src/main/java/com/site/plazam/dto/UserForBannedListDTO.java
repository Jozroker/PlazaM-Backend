package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class UserForBannedListDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private PictureDTO picture;

    private LocalDate bannedTo;

    @NotNull
    private boolean banned;

    @NotNull
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForBannedListDTO that = (UserForBannedListDTO) o;
        return banned == that.banned &&
                Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(picture, that.picture) &&
                Objects.equals(bannedTo, that.bannedTo) &&
                country == that.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, email, phone, picture, bannedTo, banned, country);
    }
}
