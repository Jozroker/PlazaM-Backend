package com.site.plazam.domain;

import org.bson.types.Binary;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public enum Country {

    UNITED_KINGDOM(generateFlagPicture("src/main/webapp/resources/img/png/en" +
            ".png")),
    UKRAINE(generateFlagPicture("src/main/webapp/resources/img/png/ua.png")),
    POLAND(generateFlagPicture("src/main/webapp/resources/img/png/pl.png"));

    private final Binary flagPicture;

    Country(Binary flagPicture) {
        this.flagPicture = flagPicture;
    }

    private static Binary generateFlagPicture(String path) {
        byte[] byteArray = new byte[0];
        try {
            BufferedImage bImage = ImageIO.read(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            byteArray = bos.toByteArray();
        } catch (Exception ignored) {
        }
        return new Binary(byteArray);
    }

    public byte[] getFlagPicture() {
        return flagPicture.getData();
    }
}
