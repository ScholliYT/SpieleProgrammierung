package de.greenfootdevz.fassspiel.util;

import greenfoot.GreenfootImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageLoader {

    public static GreenfootImageExtended getCharacterImage() {
        File characterFile = new File(System.getProperty("user.home") + "/charactereditor" + "/character.png");
        if (characterFile.exists() && characterFile.isFile()) {
            try {
                InputStream inputStream = new FileInputStream(characterFile);
                return loadImage(inputStream, "character.png");
            } catch (IOException e) {
                new ExceptionDialog(e);
            }
        }

        return null; // return null if no image was found
    }

    public static GreenfootImageExtended getDefaultCharacterImage() {
        return loadImage("/images/character", "defaultcharacter.png");
    }

    private static GreenfootImageExtended loadImage(String path, String filename) {
        return loadImage(ImageLoader.class.getResourceAsStream(path + "/" + filename), filename);
    }

    private static GreenfootImageExtended loadImage(InputStream inputstream, String filename) {
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(inputstream);
            GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
            BufferedImage gBufImg = gImage.getAwtImage();
            Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
            graphics.drawImage(bufImage, null, 0, 0);
            return new GreenfootImageExtended(gImage, filename);
        } catch (IOException e) {
            new ExceptionDialog(e);
        }
        return null;
    }
}
