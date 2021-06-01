package com.site.plazam.service.mapper;

import com.site.plazam.domain.ActorPicture;
import com.site.plazam.domain.MoviePicture;
import com.site.plazam.domain.UserPicture;
import com.site.plazam.dto.parents.PictureDTO;
import org.apache.commons.codec.binary.Base64;
import org.bson.types.Binary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    @Mapping(source = "picture", target = "picture",
            qualifiedByName = "toBinary")
    UserPicture toUserPictureEntity(PictureDTO userPictureDTO);

    @Mapping(source = "picture", target = "pictureString", qualifiedByName =
            "toStringPicture")
    @Mapping(source = "picture", target = "picture", qualifiedByName =
            "setEmpty")
    PictureDTO toDTO(UserPicture userPicture);

    @Mapping(source = "picture", target = "picture",
            qualifiedByName = "toBinary")
    MoviePicture toMoviePictureEntity(PictureDTO moviePictureDTO);

    @Mapping(source = "picture", target = "pictureString", qualifiedByName =
            "toStringPicture")
    @Mapping(source = "picture", target = "picture", qualifiedByName =
            "setEmpty")
    PictureDTO toDTO(MoviePicture moviePicture);

    @Mapping(source = "picture", target = "picture",
            qualifiedByName = "toBinary")
    ActorPicture toActorPictureEntity(PictureDTO actorPictureDTO);

    @Mapping(source = "picture", target = "pictureString", qualifiedByName =
            "toStringPicture")
    @Mapping(source = "picture", target = "picture", qualifiedByName =
            "setEmpty")
    PictureDTO toDTO(ActorPicture actorPicture);

    default Binary toBinary(byte[] picture) {
        return new Binary(picture);
    }

    default String toStringPicture(Binary binary) {
        return Base64.encodeBase64String(binary.getData());
    }

    default byte[] setEmpty(Binary binary) {
        return new byte[0];
    }
}
