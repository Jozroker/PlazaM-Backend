package com.site.plazam.service;

import com.site.plazam.domain.Technology;
import com.site.plazam.dto.HallForSeanceDTO;
import com.site.plazam.dto.HallForTicketDTO;
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
class HallServiceTest {

    static HallForSeanceDTO hallForSeanceDTO;
    static HallForTicketDTO hallForTicketDTO;
    static List<HallForSeanceDTO> hallForSeanceDTOList;
    static List<HallForTicketDTO> hallForTicketDTOList;
    static CinemaDTO cinemaDTO = new CinemaDTO() {{
        this.setId("60acd50365db1d37f453cd2f");
    }};
    static Technology technology = Technology._3D;
    static String hallId = "60acd50365db1d37f453cd32";
    @Autowired
    HallService hallService;

    @Test
    @Order(1)
    void findById() {
        hallForSeanceDTO = hallService.findHallForSeanceById(hallId);
        hallForTicketDTO = hallService.findHallForTicketById(hallId);
        System.out.println(hallForSeanceDTO.toString());
        System.out.println(hallForTicketDTO.toString());
        System.out.println("=======================");
        Assert.assertNotNull(hallForSeanceDTO.getId());
        Assert.assertNotNull(hallForTicketDTO.getId());
    }

    @Test
    @Order(2)
    void findAll() {
        hallForSeanceDTOList = hallService.findHallForSeanceAll();
        hallForTicketDTOList = hallService.findHallForTicketAll();
        System.out.println("[");
        hallForSeanceDTOList.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n-----------------------");
        System.out.println("[");
        hallForTicketDTOList.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n=======================");
        Assert.assertFalse(hallForSeanceDTOList.isEmpty());
        Assert.assertFalse(hallForTicketDTOList.isEmpty());
    }

    @Test
    @Order(3)
    void findByTech() {
        hallForSeanceDTOList =
                hallService.findHallForSeanceByTechnology(technology);
        System.out.println("[");
        hallForSeanceDTOList.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n=======================");
        Assert.assertFalse(hallForSeanceDTOList.isEmpty());
    }

    @Test
    @Order(4)
    void findByTechIsIn() {
        hallForSeanceDTOList =
                hallService.findByTechnologyIsIn(com.sun.tools.javac.util.List.of(Technology._3D, Technology._4D));
        System.out.println("[");
        hallForSeanceDTOList.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n=======================");
        Assert.assertFalse(hallForSeanceDTOList.isEmpty());
    }

    @Test
    @Order(5)
    void findByCinema() {
        hallForSeanceDTOList =
                hallService.findHallForSeanceByCinema(cinemaDTO);
        System.out.println("[");
        hallForSeanceDTOList.forEach(cinema -> System.out.println(cinema.toString() + ","));
        System.out.println("]\n=======================");
        Assert.assertFalse(hallForSeanceDTOList.isEmpty());
    }
}
