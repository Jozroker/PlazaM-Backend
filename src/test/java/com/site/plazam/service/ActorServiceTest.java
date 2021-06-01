package com.site.plazam.service;

import com.site.plazam.dto.ActorCreateDTO;
import com.site.plazam.dto.ActorForActorListDTO;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActorServiceTest {

    static ActorCreateDTO actorCreateDTO;
    static ActorForActorListDTO actorForActorListDTO;
    @Autowired
    ActorService actorService;

    @Test
    @Order(1)
    void save() {
        actorCreateDTO = new ActorCreateDTO();
        actorCreateDTO.setFirstName(new HashMap<String, String>() {{
            put("eng", "Bon");
            put("ukr", "Бон");
            put("pol", "Bon");
        }});
        actorCreateDTO.setLastName(new HashMap<String, String>() {{
            put("eng", "Jowany");
            put("ukr", "Джовані");
            put("pol", "Jowany");
        }});
        actorForActorListDTO = actorService.save(actorCreateDTO);
        System.out.println(actorForActorListDTO.toString());
        System.out.println("=======================\n");
        Assert.assertNotNull(actorForActorListDTO.getId());
    }

    @Test
    @Order(2)
    void findById() {
        actorForActorListDTO = actorService.findById(actorForActorListDTO.getId());
        System.out.println(actorForActorListDTO.toString());
        System.out.println("=======================\n");
        Assert.assertNotNull(actorForActorListDTO.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        List<ActorForActorListDTO> actors = actorService.findAll();
        System.out.println("[");
        actors.forEach(actor -> System.out.println(actor.toString() + ","));
        System.out.println("]\n=======================\n");
        Assert.assertFalse(actors.isEmpty());
    }

    @Test
    @Order(4)
    void findByName() {
        List<ActorForActorListDTO> actors =
                actorService.findByFirstNameOrLastName("Bon", "Bon");
        List<ActorForActorListDTO> actors1 =
                actorService.findByFirstNameOrLastName("Бон", "Бон");
        List<ActorForActorListDTO> actors2 =
                actorService.findByFirstNameOrLastName("Jowany", "Jowany");
        List<ActorForActorListDTO> actors3 =
                actorService.findByFirstNameOrLastName("Bon", "Jowany");
        List<ActorForActorListDTO> actors4 =
                actorService.findByFirstNameOrLastName("Бон", "Jowany");
        List<ActorForActorListDTO> actors5 =
                actorService.findByFirstNameOrLastName("ActorFirst1",
                        "ActorFirst1");
        List<ActorForActorListDTO> actors6 =
                actorService.findByFirstNameOrLastName("actor", "actor");
        List<ActorForActorListDTO> actors7 =
                actorService.findByFirstNameOrLastName("Jowany", "Бон");
        List<ActorForActorListDTO> actors8 =
                actorService.findByFirstNameOrLastName("Jowanss", "Jowanss");
        System.out.println(actors.isEmpty() ? "[]" : actors.toString());
        System.out.println(actors1.isEmpty() ? "[]" : actors1.toString());
        System.out.println(actors2.isEmpty() ? "[]" : actors2.toString());
        System.out.println(actors3.isEmpty() ? "[]" : actors3.toString());
        System.out.println(actors4.isEmpty() ? "[]" : actors4.toString());
        System.out.println(actors5.isEmpty() ? "[]" : actors5.toString());
        System.out.println(actors6.isEmpty() ? "[]" : actors6.toString());
        System.out.println(actors7.isEmpty() ? "[]" : actors7.toString());
        System.out.println(actors8.isEmpty() ? "[]" : actors8.toString());
    }

    @Test
    @Order(5)
    void delete() {
        actorService.delete(actorForActorListDTO);
        actorForActorListDTO =
                actorService.findById(actorForActorListDTO.getId());
        Assert.assertNull(actorForActorListDTO);
        System.out.println("Deleting success");
    }
}
