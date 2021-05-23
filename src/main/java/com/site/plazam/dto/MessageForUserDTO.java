package com.site.plazam.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageForUserDTO {

    private String id;

    @NotNull
    private String text;

}
