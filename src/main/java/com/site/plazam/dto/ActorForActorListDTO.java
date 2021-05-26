package com.site.plazam.dto;

import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class ActorForActorListDTO extends ActorSimpleDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private PictureDTO picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActorForActorListDTO actor = (ActorForActorListDTO) o;
        return Objects.equals(firstName, actor.firstName) &&
                Objects.equals(lastName, actor.lastName) &&
                Objects.equals(picture, actor.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, picture);
    }
}
