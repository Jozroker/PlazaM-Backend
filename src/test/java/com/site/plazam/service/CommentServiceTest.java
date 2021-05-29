package com.site.plazam.service;

import com.site.plazam.domain.Role;
import com.site.plazam.dto.*;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

    static MovieFullDTO movie;
    static UserForSelfInfoDTO user;
    static CommentCreateDTO commentCreateDTO;
    static CommentForMovieDTO commentForMovieDTO;
    static CommentForCommentsListDTO commentForCommentsListDTO;
    static List<CommentForMovieDTO> commentForMovieDTOList;
    static List<CommentForReportedListDTO> commentForReportedListDTOList;
    static List<CommentForCommentsListDTO> commentForCommentsListDTOList;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;

    @Test
    @Order(1)
    void save() {
        movie = movieService.findMovieFullById("60b24466e5ff992bedb79ef0");
        user = userService.findUserForSelfInfoById("60b24466e5ff992bedb79f04");
        commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setMovie(movie);
        commentCreateDTO.setUser(user);
        commentCreateDTO.setDate(LocalDate.now());
        commentCreateDTO.setTime(LocalDateTime.now());
        commentCreateDTO.setText("some text");
        commentForMovieDTO = commentService.save(commentCreateDTO);
        System.out.println(commentForMovieDTO.toString());
        Assert.assertNotNull(commentForMovieDTO);
    }

    @Test
    @Order(2)
    void findById() {
        commentForMovieDTO =
                commentService.findCommentForMovieById(commentForMovieDTO.getId());
        System.out.println(commentForMovieDTO);
        Assert.assertNotNull(commentForMovieDTO);
    }

    @Test
    @Order(3)
    void findAll() {
        commentForMovieDTOList = commentService.findCommentForMovieAll();
        System.out.println("[");
        commentForMovieDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForMovieDTOList.isEmpty());
    }

    @Test
    @Order(4)
    void updateCommentText() {
        commentForCommentsListDTO = new CommentForCommentsListDTO();
        commentForCommentsListDTO.setText("New comment text");
        commentForCommentsListDTO.setId(commentForMovieDTO.getId());
        commentForCommentsListDTO = commentService.updateCommentText(commentForCommentsListDTO);
        System.out.println(commentForCommentsListDTO.toString());
        Assert.assertNotNull(commentForCommentsListDTO);
    }

    @Test
    @Order(5)
    void findByUserRole() {
        commentForReportedListDTOList =
                commentService.findCommentForReportedListByUserRole(Role.USER);
        System.out.println("[");
        commentForReportedListDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForReportedListDTOList.isEmpty());
    }

    @Test
    @Order(6)
    void findByFNameOrLNameOrUsername() {
        commentForReportedListDTOList =
                commentService.findCommentForReportedListByFirstNameOrLastNameOrUsername("user", "user", "user");
        System.out.println("[");
        commentForReportedListDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForReportedListDTOList.isEmpty());
    }

    @Test
    @Order(7)
    void findByUser() {
        commentForCommentsListDTOList =
                commentService.findByUser(user);
        System.out.println("[");
        commentForCommentsListDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForCommentsListDTOList.isEmpty());
    }

    @Test
    @Order(8)
    void findByMovie() {
        commentForMovieDTOList =
                commentService.findByMovie(movie);
        System.out.println("[");
        commentForMovieDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForMovieDTOList.isEmpty());
    }

    @Test
    @Order(9)
    void findByReportedTrue() {
        commentForReportedListDTOList =
                commentService.findByReportedTrue();
        System.out.println("[");
        commentForReportedListDTOList.forEach(comment -> System.out.println(comment.toString()));
        System.out.println("]");
        Assert.assertFalse(commentForReportedListDTOList.isEmpty());
    }

    @Test
    @Order(10)
    void delete() {
        commentService.delete(commentForCommentsListDTO);
        commentForCommentsListDTO =
                commentService.findCommentForCommentsListById(commentForMovieDTO.getId());
        Assert.assertNull(commentForCommentsListDTO);
    }
}
