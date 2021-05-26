package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rating {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    @Field(name = "user_rating")
    private Double userRating;

    @NotNull
    @Field(name = "user_id")
    private String userId;

    @NotNull
    @Field(name = "movie_id")
    private String movieId;

    public Rating(@NotNull Double userRating,
                  @NotNull String userId,
                  @NotNull String movieId) {
        this.userRating = userRating;
        this.userId = userId;
        this.movieId = movieId;
    }
}
