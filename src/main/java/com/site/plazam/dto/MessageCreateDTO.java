package com.site.plazam.dto;

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
public class MessageCreateDTO {

    private String id;

    @NotNull
    @NotEmpty
    private Map<String, String> text = new HashMap<>();

}
