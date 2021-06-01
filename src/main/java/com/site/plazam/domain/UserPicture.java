package com.site.plazam.domain;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "user_picture")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserPicture {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @Field(name = "binary_picture")
    private Binary picture;

    @NotNull
    private String format;

    public UserPicture(@NotNull Binary picture) {
        this.picture = picture;
    }
}
