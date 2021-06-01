package com.site.plazam.service.mapper;

import com.site.plazam.domain.Actor;
import com.site.plazam.domain.ActorPicture;
import com.site.plazam.domain.Lang;
import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.PictureService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class ActorMapper {

    @Autowired
    PictureService ps;

    @Mapping(source = "picture", target = "pictureId", qualifiedByName =
            "toPictureId")
    public abstract Actor toEntity(ActorCreateDTO actorCreateDTO);

    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    @Mapping(source = "firstName", target = "firstName", qualifiedByName =
            "toString")
    @Mapping(source = "lastName", target = "lastName", qualifiedByName =
            "toString")
    public abstract ActorForActorListDTO toActorForActorListDTO(Actor actor);

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

    PictureDTO toPicture(String id) {
        if (id == null) {
            PictureDTO pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp/resources/img/jpg/default_avatar.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpeg", bos);
                pictureDTO.setPicture(bos.toByteArray());
            } catch (Exception ignored) {
            }
            return pictureDTO;
        }
        return ps.findById(id, ActorPicture.class);
    }
}
