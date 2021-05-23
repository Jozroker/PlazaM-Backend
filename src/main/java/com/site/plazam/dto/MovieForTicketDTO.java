package com.site.plazam.dto;

import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForTicketDTO extends MovieForHomeSliderDTO {

    @NotNull
    private PictureDTO posterPicture;

}
