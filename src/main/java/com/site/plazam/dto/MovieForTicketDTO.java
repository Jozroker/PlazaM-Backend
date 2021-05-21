package com.site.plazam.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieForTicketDTO extends MovieForHomeSliderDTO {

    @NotNull
    @NotEmpty
    private byte[] posterPicture = new byte[0];

}
