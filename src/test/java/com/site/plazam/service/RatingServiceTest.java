package com.site.plazam.service;

import com.site.plazam.dto.MovieFullDTO;
import com.site.plazam.dto.RatingCreateDTO;
import com.site.plazam.dto.UserForSelfInfoDTO;
import com.site.plazam.dto.parents.RatingSimpleDTO;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RatingServiceTest {

    static MovieFullDTO movie;
    static UserForSelfInfoDTO user;
    static RatingCreateDTO ratingCreateDTO;
    static RatingSimpleDTO ratingSimpleDTO;
    static List<RatingSimpleDTO> ratingSimpleDTOList;
    @Autowired
    RatingService ratingService;
    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;

    @Test
    @Order(1)
    void save() {
        movie = movieService.findMovieFullById("60b24466e5ff992bedb79ef2");
        user = userService.findUserForSelfInfoById("60b24466e5ff992bedb79f04");
        ratingCreateDTO = new RatingCreateDTO();
        ratingCreateDTO.setMovie(movie);
        ratingCreateDTO.setUser(user);
        ratingCreateDTO.setUserRating(2.3);
        ratingSimpleDTO = ratingService.save(ratingCreateDTO);
        System.out.println(ratingSimpleDTO);
        Assert.assertNotNull(ratingSimpleDTO);
    }

    @Test
    @Order(2)
    void findById() {
        ratingSimpleDTO = ratingService.findById(ratingSimpleDTO.getId());
        System.out.println(ratingSimpleDTO);
        Assert.assertNotNull(ratingSimpleDTO);
    }

    @Test
    @Order(3)
    void findAll() {
        ratingSimpleDTOList = ratingService.findAll();
        System.out.println("[");
        ratingSimpleDTOList.forEach(rating -> System.out.println(rating.toString()));
        System.out.println("]");
        Assert.assertFalse(ratingSimpleDTOList.isEmpty());
    }

    @Test
    @Order(4)
    void findByUser() {
        ratingSimpleDTOList = ratingService.findByUser(user);
        System.out.println("[");
        ratingSimpleDTOList.forEach(rating -> System.out.println(rating.toString()));
        System.out.println("]");
        Assert.assertFalse(ratingSimpleDTOList.isEmpty());
    }

    @Test
    @Order(5)
    void findByMovie() {
        ratingSimpleDTOList = ratingService.findByMovie(movie);
        System.out.println("[");
        ratingSimpleDTOList.forEach(rating -> System.out.println(rating.toString()));
        System.out.println("]");
        Assert.assertFalse(ratingSimpleDTOList.isEmpty());
    }

    @Test
    @Order(6)
    void findByUserAndMovie() {
        ratingSimpleDTO = ratingService.findByUserAndMovie(user, movie);
        System.out.println(ratingSimpleDTO.toString());
        Assert.assertNotNull(ratingSimpleDTO);
    }

    @Test
    @Order(7)
    void delete() {
        ratingService.delete(ratingSimpleDTO);
        ratingSimpleDTO = ratingService.findById(ratingSimpleDTO.getId());
        Assert.assertNull(ratingSimpleDTO);
    }
}
