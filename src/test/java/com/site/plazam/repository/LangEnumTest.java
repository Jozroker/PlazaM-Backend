package com.site.plazam.repository;

import com.site.plazam.domain.Lang;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LangEnumTest {

    @Test
    @Order(1)
    public void testLangBinaryPicture() {
        Lang lang = Lang.ENG;
        System.out.println(lang.toString());
    }
}
