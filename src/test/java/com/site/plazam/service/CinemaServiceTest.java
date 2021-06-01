package com.site.plazam.service;

import com.site.plazam.dto.parents.CinemaDTO;
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
class CinemaServiceTest {

    static CinemaDTO cinemaDTO;
    @Autowired
    CinemaService cinemaService;

    @Test
    @Order(2)
    void findById() {
        cinemaDTO = cinemaService.findById(cinemaDTO.getId());
        System.out.println(cinemaDTO.toString());
        System.out.println("=======================");
        Assert.assertNotNull(cinemaDTO.getId());
    }

    @Test
    @Order(1)
    void findAll() {
        List<CinemaDTO> cinemas = cinemaService.findAll();
        System.out.println("[");
        cinemas.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n=======================");
        if (!cinemas.isEmpty()) {
            cinemaDTO = cinemas.get(0);
        }
        Assert.assertFalse(cinemas.isEmpty());
    }
}
