package com.site.plazam.service.mapper;

import com.site.plazam.domain.Comment;
import com.site.plazam.dto.*;
import com.site.plazam.service.MovieService;
import com.site.plazam.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    final UserService us;

    final MovieService ms;

    protected CommentMapper(UserService userService, MovieService movieService) {
        this.us = userService;
        this.ms = movieService;
    }

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "movie.id", target = "movieId")
    @Mapping(source = "rating.id", target = "ratingId")
    public abstract Comment toEntity(CommentCreateDTO commentCreateDTO);

    @Mapping(source = "movieId", target = "movie", qualifiedByName = "toMovie")
    public abstract CommentForCommentsListDTO toCommentForCommentsListDTO(Comment comment);

    @Mapping(source = "userId", target = "user", qualifiedByName =
            "toUserForComment")
    public abstract CommentForMovieDTO toCommentForMovieDTO(Comment comment);

    @Mapping(source = "userId", target = "user", qualifiedByName =
            "toUserForReportedList")
    public abstract CommentForReportedListDTO toCommentForReportedListDTO(Comment comment);

    MovieForCommentDTO toMovie(String id) {
        return ms.findMovieForCommentById(id);
    }

    UserForCommentDTO toUserForComment(String id) {
        return us.findUserForCommentById(id);
    }

    UserForReportedListDTO toUserForReportedList(String id) {
        return us.findUserForReportedListById(id);
    }
}
