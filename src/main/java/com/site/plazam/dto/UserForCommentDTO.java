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
public class UserForCommentDTO extends UserSimpleDTO {

    @NotNull
    private String name;

    @NotNull
    private PictureDTO picture;

    @NotNull
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserForCommentDTO that = (UserForCommentDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(picture, that.picture) &&
                country == that.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, picture, country);
    }
}
