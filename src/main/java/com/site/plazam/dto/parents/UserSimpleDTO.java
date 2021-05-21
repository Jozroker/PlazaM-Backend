package com.site.plazam.dto.parents;

import com.site.plazam.domain.Role;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserSimpleDTO {

    private String id;

    @NotNull
    private Role role = Role.USER;
}
