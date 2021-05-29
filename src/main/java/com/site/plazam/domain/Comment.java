package com.site.plazam.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @Indexed(unique = true)
    private String id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDateTime time;

    @NotNull
    private String text;

    private Boolean reported = false;

    @NotNull
    @Field(name = "user_id")
    private String userId;

    @NotNull
    @Field(name = "movie_id")
    private String movieId;

    @Field(name = "movie_rating_id")
    private String ratingId;

    public Comment(@NotNull LocalDate date,
                   @NotNull LocalDateTime time,
                   @NotNull String text,
                   @NotNull String userId,
                   @NotNull String movieId) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.userId = userId;
        this.movieId = movieId;
    }

    public Comment(@NotNull LocalDate date,
                   @NotNull LocalDateTime time,
                   @NotNull String text,
                   Boolean reported,
                   @NotNull String userId,
                   @NotNull String movieId) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.reported = reported;
        this.userId = userId;
        this.movieId = movieId;
    }
}
