package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "actor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Actor {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @NotEmpty
    @Field(name = "first_name")
    private Map<String, String> firstName = new HashMap<>();

    @NotNull
    @NotEmpty
    @Field(name = "last_name")
    private Map<String, String> lastName = new HashMap<>();

    @Field(name = "actor_picture_id")
    private String pictureId;

    public Actor(@NotNull @NotEmpty Map<String, String> firstName,
                 @NotNull @NotEmpty Map<String, String> lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Actor(@NotNull @NotEmpty Map<String, String> firstName,
                 @NotNull @NotEmpty Map<String, String> lastName,
                 String pictureId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pictureId = pictureId;
    }
}
