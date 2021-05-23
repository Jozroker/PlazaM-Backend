package com.site.plazam.dto;

import com.site.plazam.domain.Country;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForCommentDTO extends UserSimpleDTO {

    @NotNull
    private String name;

    @NotNull
    private PictureDTO picture;

    @NotNull
    private Country homeCountry;
}
