package de.greenfootdevz.charactereditor.Actor;

import greenfoot.*;
import java.awt.*;

public class BodyPart extends Actor {


    private final GreenfootImage[] images;
    private final Point offset;
    private String name;
    private int currentImage;

    public String getName() {
        return name;
    }

    public BodyPart(String name, GreenfootImage[] images, Point offset) throws IllegalArgumentException{
        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("images must be an array with at least one element");
        }
        this.name = name;
        this.images = images;
        this.offset = offset;
        currentImage = 0;
        setCurrentImage();
    }

    public void act() {

    }

    public void previous() {
        currentImage--;
        if (currentImage < 0) {
            currentImage = images.length - 1;
        }
        setCurrentImage();
    }

    public void next() {
        currentImage++;
        if (currentImage == images.length) {
            currentImage = 0;
        }
        setCurrentImage();
    }

    private void setCurrentImage() {
        setImage(images[currentImage]);
    }

    public Point getOffset() {
        return offset;
    }
}