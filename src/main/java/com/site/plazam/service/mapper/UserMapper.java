package com.site.plazam.service.mapper;

import com.site.plazam.domain.ActorPicture;
import com.site.plazam.domain.User;
import com.site.plazam.dto.*;
import com.site.plazam.dto.parents.CinemaDTO;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.dto.parents.TicketSimpleDTO;
import com.site.plazam.service.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    PictureService ps;

    @Autowired
    MovieService ms;

    @Autowired
    TicketService ts;

    @Autowired
    MessageService mss;

    @Autowired
    CinemaService cs;

    public abstract User toEntity(UserForRegistrationDTO userForRegistrationDTO);

    public abstract UserForResultListDTO toUserForResultListDTO(User user);

    @Mapping(source = "homeCountry", target = "country")
    @Mapping(source = "bannedToDate", target = "bannedTo")
    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    public abstract UserForBannedListDTO toUserForBannedListDTO(User user);

    @Mapping(source = "homeCountry", target = "country")
    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    public abstract UserForCommentDTO toUserForCommentDTO(User user);

    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    public abstract UserForReportedListDTO toUserForReportedListDTO(User user);

    @Mapping(source = "homeCountry", target = "country")
    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    @Mapping(source = "ticketIds", target = "tickets", qualifiedByName =
            "toTicket")
    @Mapping(source = "messageIds", target = "messages", qualifiedByName =
            "toMessage")
    @Mapping(source = "selectedCinemaId", target = "selectedCinema",
            qualifiedByName = "toCinema")
    @Mapping(source = "favouriteMovieIds", target = "favouriteMovies",
            qualifiedByName = "toMovie")
    @Mapping(source = "viewedMovieIds", target = "viewedMovies",
            qualifiedByName = "toMovie")
    @Mapping(source = "waitMovieIds", target = "waitMovies",
            qualifiedByName = "toMovie")
    public abstract UserForSelfInfoDTO toUserForSelfInfoDTO(User user);

    @Mapping(source = "homeCountry", target = "country")
    @Mapping(source = "pictureId", target = "picture", qualifiedByName =
            "toPicture")
    public abstract UserForUsersListDTO toUserForUsersListDTO(User user);


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

    List<TicketSimpleDTO> toTicket(List<String> ticketIds) {
        return ticketIds.stream().map(ts::findById).collect(Collectors.toList());
    }

    List<MessageForUserDTO> toMessage(List<String> messageIds) {
        return messageIds.stream().map(mss::findById).collect(Collectors.toList());
    }

    CinemaDTO toCinema(String id) {
        return cs.findById(id);
    }

    List<MovieForMoviesListDTO> toMovie(List<String> movieIds) {
        return movieIds.stream().map(ms::findMovieForMoviesListById).collect(Collectors.toList());
    }
}
