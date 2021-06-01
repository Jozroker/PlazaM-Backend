package com.site.plazam.plazam;

import com.site.plazam.domain.MoviePicture;
import com.site.plazam.dto.parents.PictureDTO;
import com.site.plazam.service.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Autowired
    PictureService pictureService;

    @Test
    public void test() {
        PictureDTO picture = new PictureDTO();

        try {
            File file = new File("J:\\PC_Educate\\Programming\\java\\plazam\\src" +
                    "\\main\\webapp\\resources\\img\\jpg\\wide\\hall.jpg");
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", bos);
            byte[] data = bos.toByteArray();
            picture.setPicture(data);
        } catch (Exception ignore) {
        }

        picture.setFormat("jpg");
        pictureService.save(picture, MoviePicture.class);
    }
}
