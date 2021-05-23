package com.site.plazam.service.mapper;

import com.site.plazam.domain.Actor;
import com.site.plazam.domain.ActorPicture;
import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.PictureService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Component
@Mapper(componentModel = "spring")
public abstract class ActorMapper {

    final PictureService ps;

    protected ActorMapper(PictureService pictureService) {
        this.ps = pictureService;
    }

    @Mapping(source = "picture.id", target = "pictureId")
    public abstract Actor toEntity(ActorCreateDTO actorCreateDTO);

    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    public abstract ActorForActorListDTO toActorForActorListDTO(Actor actor);

    PictureDTO toPicture(String id) {
        PictureDTO pictureDTO;
        if (id == null) {
            pictureDTO = new PictureDTO();
            try {
                BufferedImage bImage = ImageIO.read(new File("src/main/webapp/resources/img/jpg/default_avatar.jpg"));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                pictureDTO.setPicture(bos.toByteArray());
            } catch (Exception ignored) {
            }
        } else {
            pictureDTO = ps.findById(id, ActorPicture.class);
        }
        return pictureDTO;
    }
}
