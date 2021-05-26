package com.site.plazam.dto;

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
public class UserForReportedListDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private PictureDTO picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForReportedListDTO that = (UserForReportedListDTO) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, picture);
    }
}
