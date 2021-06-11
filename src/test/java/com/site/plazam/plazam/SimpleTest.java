package com.site.plazam.plazam;

import com.site.plazam.domain.User;
import com.site.plazam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() {
        User user = userRepository.findByTicketIdsContains(
                "60c2a41b5d59a6012463d5eb").orElse(null);
        System.out.println(user);
    }
}
