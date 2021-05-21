package com.site.plazam.dto.parents;

import com.site.plazam.domain.Technology;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallSimpleDTO {

    private String id;

    @NotNull
    private Technology technology;
}
