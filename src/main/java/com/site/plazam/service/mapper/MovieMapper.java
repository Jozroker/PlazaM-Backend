package com.site.plazam.service.mapper;

import com.site.plazam.domain.Lang;
import com.site.plazam.domain.Movie;
import com.site.plazam.domain.MoviePicture;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.ActorService;
import com.site.plazam.service.PictureService;
import org.bson.internal.Base64;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MovieMapper {

    @Autowired
    PictureService ps;

    @Autowired
    ActorService as;

    @Mapping(source = "widePicture", target = "widePictureId",
            qualifiedByName = "toPictureId")
    @Mapping(source = "posterPicture", target = "posterPictureId",
            qualifiedByName = "toPictureId")
    @Mapping(source = "galleryPictures", target = "galleryPictureIds",
            qualifiedByName = "toPictureIdList")
    @Mapping(source = "actors", target = "actorIds",
            qualifiedByName = "toActorIdList")
    public abstract Movie toEntity(MovieCreateDTO movieCreateDTO);

    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "widePictureId", target = "widePicture",
            qualifiedByName = "toWidePicture")
    @Mapping(source = "galleryPictureIds", target = "galleryPictures",
            qualifiedByName = "toPictureList")
    @Mapping(source = "actorIds", target = "actors",
            qualifiedByName = "toActorList")
    public abstract MovieCreateDTO toMovieCreateDTO(Movie movie);

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

    //
    @Mapping(source = "name", target = "name", qualifiedByName = "toString")
    @Mapping(source = "surname", target = "surname", qualifiedByName = "toString")
    @Mapping(source = "posterPictureId", target = "posterPicture",
            qualifiedByName = "toPosterPicture")
    @Mapping(source = "duration", target = "durationInMinutes")
    public abstract MovieForSeanceDTO toMovieForSeanceDTO(Movie movie);

    //
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
        Lang locale;
        try {
            locale =
                    Lang.valueOf(LocaleContextHolder.getLocale().getISO3Language().toUpperCase());
        } catch (Exception e) {
            locale =
                    Lang.ENG;
        }
        return map.get(locale.name().toLowerCase());
    }

    String toPictureId(PictureDTO pictureDTO) {
        if (pictureDTO == null) {
            return null;
        }
        return pictureDTO.getId();
    }

    @Named("toPosterPicture")
    PictureDTO toPosterPicture(String id) {
        if (id == null) {
            PictureDTO pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp" +
                        "/resources/img/jpg/poster/default_poster.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                pictureDTO.setPictureString(Base64.encode(bos.toByteArray()));
                pictureDTO.setFormat("jpg");
            } catch (Exception ignored) {
            }
            return pictureDTO;
        }
        return ps.findById(id, MoviePicture.class);
    }

    @Named("toWidePicture")
    PictureDTO toWidePicture(String id) {
        if (id == null) {
            PictureDTO pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp" +
                        "/resources/img/jpg/wide/default_wide.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                pictureDTO.setPictureString(Base64.encode(bos.toByteArray()));
                pictureDTO.setFormat("jpg");
            } catch (Exception ignored) {
            }
            return pictureDTO;
        }
        return ps.findById(id, MoviePicture.class);
    }

    List<PictureDTO> toPictureList(List<String> pictureIds) {
        return pictureIds.stream().map(id -> ps.findById(id,
                MoviePicture.class)).collect(Collectors.toList());
    }

    List<String> toPictureIdList(List<PictureDTO> pictures) {
        return pictures.stream().map(picture -> {
            if (picture.getId() == null) {
                picture = ps.save(picture, MoviePicture.class);
            }
            return picture.getId();
        }).collect(Collectors.toList());
    }

    List<ActorForActorListDTO> toActorList(List<String> actorIds) {
        return actorIds.stream().map(as::findById).collect(Collectors.toList());
    }

    List<String> toActorIdList(List<ActorForActorListDTO> pictures) {
        return pictures.stream().map(ActorForActorListDTO::getId).collect(Collectors.toList());
    }
}
