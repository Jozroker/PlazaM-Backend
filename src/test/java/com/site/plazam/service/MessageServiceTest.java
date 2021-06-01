package com.site.plazam.service;

import com.site.plazam.dto.MessageCreateDTO;
import com.site.plazam.dto.MessageForUserDTO;
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
class MessageServiceTest {

    static MessageCreateDTO messageCreateDTO;
    static MessageForUserDTO messageForUserDTO;
    @Autowired
    MessageService messageService;

    @Test
    @Order(1)
    void save() {
        messageCreateDTO = new MessageCreateDTO();
        messageCreateDTO.setText(new HashMap<String, String>() {{
            put("eng", "message11");
            put("ukr", "повідомлення11");
            put("pol", "message11");
        }});
        messageForUserDTO = messageService.save(messageCreateDTO);
        System.out.println(messageForUserDTO.toString());
        System.out.println("=======================");
        Assert.assertNotNull(messageForUserDTO.getId());
    }

    @Test
    @Order(2)
    void findById() {
        messageForUserDTO = messageService.findById(messageForUserDTO.getId());
        System.out.println(messageForUserDTO.toString());
        System.out.println("]\n=======================");
        Assert.assertNotNull(messageForUserDTO.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        List<MessageForUserDTO> messages = messageService.findAll();
        System.out.println("[");
        messages.forEach(message -> System.out.println(message.toString() + ","));
        System.out.println("]\n=======================");
        Assert.assertFalse(messages.isEmpty());
    }

    @Test
    @Order(4)
    void delete() {
        messageService.delete(messageForUserDTO);
        messageForUserDTO = messageService.findById(messageForUserDTO.getId());
        Assert.assertNull(messageForUserDTO);
    }
}
