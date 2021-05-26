package com.site.plazam.dto;

import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ActorCreateDTO extends ActorSimpleDTO {

    @NotNull
    @NotEmpty
    private Map<String, String> firstName = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> lastName = new HashMap<>();

    private PictureDTO picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorCreateDTO that = (ActorCreateDTO) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, picture);
    }
}
