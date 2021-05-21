package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @NotEmpty
    private Map<String, String> text = new HashMap<>();

    public Message(@NotNull @NotEmpty Map<String, String> text) {
        this.text = text;
    }
}
