package com.site.plazam.dto;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import com.site.plazam.dto.parents.PictureDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieFullDTO extends MovieSimpleDTO {

    @NotNull
    private String name;

    private String surname;

    @NotNull
    private PictureDTO posterPicture;

    @NotNull
    private PictureDTO widePicture;

    @NotNull
    private String description;

    @NotNull
    private int durationInMinutes;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private float usersRating = 0F;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private float imdbRating = 0F;

    @NotNull
    private String directedBy;

    @NotNull
    private String movieLang;

    @NotNull
    private String movieCountry;

    private List<PictureDTO> galleryPictures = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<ActorForActorListDTO> actors = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<Genre> genres = new ArrayList<>();

}
