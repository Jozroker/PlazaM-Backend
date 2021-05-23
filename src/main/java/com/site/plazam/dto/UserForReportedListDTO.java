package com.site.plazam.dto;

import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.UserSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserForReportedListDTO extends UserSimpleDTO {

    @NotNull
    private String username;

    @NotNull
    private PictureDTO picture;

}
