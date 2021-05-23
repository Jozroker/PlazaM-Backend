package com.site.plazam.dto;

import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ActorForActorListDTO extends ActorSimpleDTO {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private PictureDTO picture;

}
