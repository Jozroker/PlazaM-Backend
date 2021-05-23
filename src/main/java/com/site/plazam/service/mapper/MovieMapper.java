package com.site.plazam.service.mapper;

import com.site.plazam.domain.Movie;
import com.site.plazam.domain.MoviePicture;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.ActorService;
import com.site.plazam.service.PictureService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public abstract class MovieMapper {

    final PictureService ps;

    final ActorService as;

    protected MovieMapper(PictureService pictureService, ActorService actorService) {
        this.ps = pictureService;
        this.as = actorService;
    }

    @Mapping(source = "widePicture.id", target = "widePictureId")
    @Mapping(source = "posterPicture.id", target = "posterPictureId")
    @Mapping(source = "galleryPictures", target = "galleryPictureIds",
            qualifiedByName = "toPictureIdList")
    @Mapping(source = "actors", target = "actorIds",
            qualifiedByName = "toActorIdList")
    public abstract Movie toEntity(MovieCreateDTO movieCreateDTO);

//    @Mapping(source = "posterPictureId", target = "posterPicture",
//            qualifiedByName = "toPosterPicture")
//    @Mapping(source = "widePictureId", target = "widePicture",
//            qualifiedByName = "toWidePicture")
//    @Mapping(source = "galleryPictureIds", target = "galleryPictures",
//            qualifiedByName = "toPictureList")
//    @Mapping(source = "actorIds", target = "actors",
//            qualifiedByName = "toActorList")
//    public abstract MovieCreateDTO toMovieCreateDTO(Movie movie);

    @Mapping(source = "fullName", target = "fullName", qualifiedByName =
            "toString")
    public abstract MovieForResultListDTO toMovieForResultListDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    public abstract MovieForComingSoonDTO toMovieForComingSoonDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "widePictureId", target = "widePicture",
            qualifiedByName = "toWidePicture")
    public abstract MovieForHomeSliderDTO toMovieForHomeSliderDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "widePictureId", target = "widePicture",
            qualifiedByName = "toWidePicture")
    public abstract MovieForCommentDTO toMovieForCommentDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "duration", target = "durationInMinutes")
    public abstract MovieForMoviesListDTO toMovieForMoviesListDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "duration", target = "durationInMinutes")
    public abstract MovieForSeanceDTO toMovieForSeanceDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "widePictureId", target = "widePicture",
            qualifiedByName = "toWidePicture")
    public abstract MovieForTicketDTO toMovieForTicketDTO(Movie movie);

    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "description", target = "description", qualifiedByName =
            "toString")
    @Mapping(source = "directedBy", target = "directedBy", qualifiedByName =
            "toString")
    @Mapping(source = "movieLang", target = "movieLang", qualifiedByName =
            "toString")
    @Mapping(source = "movieCountry", target = "movieCountry",
            qualifiedByName =
                    "toString")
    @Mapping(source = "duration", target = "durationInMinutes")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "widePictureId", target = "widePicture",
            qualifiedByName = "toWidePicture")
    @Mapping(source = "galleryPictureIds", target = "galleryPictures",
            qualifiedByName = "toPictureList")
    @Mapping(source = "actorIds", target = "actors",
            qualifiedByName = "toActorList")
    public abstract MovieFullDTO toMovieFullDTO(Movie movie);


    String toString(Map<String, String> map) {
        return map.get(LocaleContextHolder.getLocale().getLanguage());
    }

    PictureDTO toPosterPicture(String id) {
        PictureDTO pictureDTO;
        if (id == null) {
            pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp" +
                        "/resources/img/jpg/poster/default_poster.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                pictureDTO.setPicture(bos.toByteArray());
            } catch (Exception ignored) {
            }
        } else {
            pictureDTO = ps.findById(id, MoviePicture.class);
        }
        return pictureDTO;
    }

    PictureDTO toWidePicture(String id) {
        PictureDTO pictureDTO;
        if (id == null) {
            pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp" +
                        "/resources/img/jpg/wide/default_wide.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                pictureDTO.setPicture(bos.toByteArray());
            } catch (Exception ignored) {
            }
        } else {
            pictureDTO = ps.findById(id, MoviePicture.class);
        }
        return pictureDTO;
    }

    List<PictureDTO> toPictureList(List<String> pictureIds) {
        return pictureIds.stream().map(id -> ps.findById(id,
                MoviePicture.class)).collect(Collectors.toList());
    }

    List<String> toPictureIdList(List<PictureDTO> pictures) {
        return pictures.stream().map(PictureDTO::getId).collect(Collectors.toList());
    }

    List<ActorForActorListDTO> toActorList(List<String> actorIds) {
        return actorIds.stream().map(as::findById).collect(Collectors.toList());
    }

    List<String> toActorIdList(List<ActorForActorListDTO> pictures) {
        return pictures.stream().map(ActorForActorListDTO::getId).collect(Collectors.toList());
    }
}
