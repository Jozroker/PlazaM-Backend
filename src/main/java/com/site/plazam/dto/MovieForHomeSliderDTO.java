package com.site.plazam.dto;

import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForHomeSliderDTO extends MovieSimpleDTO {

    @NotNull
    @NotEmpty
    private byte[] widePicture = new byte[0];

}
