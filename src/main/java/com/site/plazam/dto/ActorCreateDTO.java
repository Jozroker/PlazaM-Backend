package com.site.plazam.dto;

import com.site.plazam.dto.parents.ActorSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ActorCreateDTO extends ActorSimpleDTO {

    @NotNull
    @NotEmpty
    private Map<String, String> firstName = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> lastName = new HashMap<>();

    @NotNull
    private PictureDTO picture;

}
