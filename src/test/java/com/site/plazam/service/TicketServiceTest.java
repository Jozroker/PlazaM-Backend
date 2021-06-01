package com.site.plazam.service;

import com.site.plazam.dto.parents.TicketSimpleDTO;
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
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketServiceTest {

    static TicketSimpleDTO ticketSimpleDTO;
    static List<TicketSimpleDTO> ticketSimpleDTOList;
    static String seanceId = "60ad66768d4de56140f4b532";
    @Autowired
    TicketService ticketService;
    @Autowired
    SeanceService seanceService;

    @Test
    @Order(1)
    void save() {
        ticketSimpleDTO = new TicketSimpleDTO();
        ticketSimpleDTO.setPlaceRow(5);
        ticketSimpleDTO.setPlaceSeat(2);
        ticketSimpleDTO.setSeance(seanceService.findSeanceForTicketById(seanceId));
        ticketSimpleDTO.setDate(LocalDate.of(2021, 5, 22));
        ticketSimpleDTO = ticketService.save(ticketSimpleDTO);
        System.out.println(ticketSimpleDTO.toString());
        System.out.println("===================================");
    }

    @Test
    @Order(2)
    void findById() {
        ticketSimpleDTO = ticketService.findById(ticketSimpleDTO.getId());
        System.out.println(ticketSimpleDTO.toString());
        System.out.println("===================================");
        Assert.assertNotNull(ticketSimpleDTO);
    }

    @Test
    @Order(3)
    void findAll() {
        ticketSimpleDTOList = ticketService.findAll();
        System.out.println("[");
        ticketSimpleDTOList.forEach(ticket -> System.out.println(ticket.toString()));
        System.out.println("]");
        Assert.assertFalse(ticketSimpleDTOList.isEmpty());
    }

    @Test
    @Order(4)
    void findBySeance() {
        ticketSimpleDTOList = ticketService.findBySeance(ticketSimpleDTO.getSeance());
        System.out.println("[");
        ticketSimpleDTOList.forEach(ticket -> System.out.println(ticket.toString()));
        System.out.println("]");
        Assert.assertFalse(ticketSimpleDTOList.isEmpty());
    }

    @Test
    @Order(5)
    void findByDate() {
        ticketSimpleDTOList = ticketService.findByDate(LocalDate.of(2021, 5, 25));
        System.out.println("[");
        ticketSimpleDTOList.forEach(ticket -> System.out.println(ticket.toString()));
        System.out.println("]");
        Assert.assertFalse(ticketSimpleDTOList.isEmpty());
    }

    @Test
    @Order(6)
    void delete() {
        ticketService.delete(ticketSimpleDTO);
        ticketSimpleDTO = ticketService.findById(ticketSimpleDTO.getId());
        Assert.assertNull(ticketSimpleDTO);
    }

}
