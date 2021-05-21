package com.site.plazam.dto;

import com.site.plazam.domain.Genre;
import com.site.plazam.domain.MPAA;
import com.site.plazam.dto.parents.MovieSimpleDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieCreateDTO extends MovieSimpleDTO {

    @NotNull
    @NotEmpty
    private byte[] posterPicture = new byte[0];

    @NotNull
    @NotEmpty
    private byte[] widePicture = new byte[0];

    @NotNull
    @NotEmpty
    private byte[] wideLittlePicture = new byte[0];

    @NotNull
    @NotEmpty
    private Map<String, String> description = new HashMap<>();

    @NotNull
    private int duration;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private float usersRating = 0F;

    @NotNull
    private MPAA mpaaRating;

    @NotNull
    private float imdbRating = 0F;

    @NotNull
    @NotEmpty
    private Map<String, String> directedBy = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> movieLang = new HashMap<>();

    @NotNull
    @NotEmpty
    private Map<String, String> movieCountry = new HashMap<>();

    private List<byte[]> galleryPictures = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<ActorForActorListDTO> actors = new ArrayList<>();

    @NotNull
    @NotEmpty
    private List<Genre> genres = new ArrayList<>();

}
