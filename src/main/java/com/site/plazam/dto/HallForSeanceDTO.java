package com.site.plazam.dto;

import com.site.plazam.dto.parents.HallSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallForSeanceDTO extends HallSimpleDTO {

    @NotNull
    private int number;

}
