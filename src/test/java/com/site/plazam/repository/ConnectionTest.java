package com.site.plazam.repository;

import com.site.plazam.domain.Cinema;
import com.site.plazam.domain.Hall;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConnectionTest {

    private static Cinema c;
    private static Hall h1;
    private static Hall h2;
    @Autowired
    CinemaRepository cr;
    @Autowired
    HallRepository hr;

    void function() {
//        c = new Cinema(new HashMap<String, String>() {{
//            put("en", "The USA");
//            put("ua", "США");
//            put("pl", "USA");
//        }}, new HashMap<String, String>() {{
//            put("en", "London");
//            put("ua", "Лондон");
//            put("pl", "London");
//        }}, new HashMap<String, String>() {{
//            put("en", "Street 1");
//            put("ua", "Вулиця 1");
//            put("pl", "Street 1");
//        }});
//
//        h1 = new Hall(1,10, 10, Technology._3D);
//        h2 = new Hall(2, 5, 20, Technology._4D);

    }

    @Test
    @Order(1)
    void separatePersist() {
        function();

        c = cr.save(c);

        h1.setCinemaId(c.getId());
        h2.setCinemaId(c.getId());

        h1 = hr.save(h1);
        h2 = hr.save(h2);

        h1.setColumns(35);
        h1 = hr.save(h1);
    }

    @Test
    @Order(2)
    void findAll() {
        hr.findAll().forEach(x -> System.out.println(x.toString()));
        System.out.println("1========================");
    }

    @Test
    @Order(3)
    void findById() {
        Optional<Cinema> cinema = cr.findById(c.getId());
        System.out.println(cinema.isPresent() ? cinema.toString() : "null");
        System.out.println("2========================");
    }

    @Test
    @Order(4)
    void findByCinemaId() {
        List<Hall> hall = hr.findByCinemaId(c.getId());
        if (hall.isEmpty()) {
            System.out.println("empty");
        } else {
            hall.forEach(x -> System.out.println(x.toString()));
        }
        System.out.println("3========================");
    }

    @Test
    @Order(5)
    void findByTech() {
        List<Hall> hall = hr.findByTechnology(h1.getTechnology());
        if (hall.isEmpty()) {
            System.out.println("empty");
        } else {
            hall.forEach(x -> System.out.println(x.toString()));
        }
        System.out.println("4========================");
    }

    @Test
    @Order(6)
    void removeCinema() {
        Optional<Cinema> cinema = cr.findById(c.getId());
        System.out.println(cinema.isPresent() ? cinema.toString() : "null");
        cr.delete(c);
        cinema = cr.findById(c.getId());
        System.out.println(cinema.isPresent() ? cinema.toString() : "null");
        System.out.println("5========================");
    }

    @Test
    @Order(7)
    void removeHallById() {
        Optional<Hall> hall = hr.findById(h2.getId());
        System.out.println(hall.isPresent() ? hall.toString() : "null");
        hr.deleteById(h2.getId());
        hall = hr.findById(h2.getId());
        System.out.println(hall.isPresent() ? hall.toString() : "null");
        System.out.println("6========================");
    }

    @Test
    @Order(8)
    void deleteAll() {
        cr.deleteAll();
        hr.deleteAll();
    }
}
