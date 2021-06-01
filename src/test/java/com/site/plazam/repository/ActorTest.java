package com.site.plazam.repository;

import com.site.plazam.domain.Actor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ActorTest {

    @Autowired
    ActorRepository ar;

    List<Actor> actor;

    @Test
    void createActor() {
        actor = ar.findByFirstNameOrLastName("Da", "D");
        System.out.println(actor);
    }
}
