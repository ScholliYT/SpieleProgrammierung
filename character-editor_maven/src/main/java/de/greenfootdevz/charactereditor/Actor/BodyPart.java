package de.greenfootdevz.charactereditor.Actor;

import com.sun.istack.internal.NotNull;
import com.sun.javaws.exceptions.InvalidArgumentException;
import greenfoot.*;

import java.awt.*;

import static greenfoot.Greenfoot.isKeyDown;

public class BodyPart extends Actor {


    private final GreenfootImage[] images;
    private final Point offset;
    private String name;
    private int currentImage;

    public String getName() {
        return name;
    }

    public BodyPart(@NotNull String name, @NotNull GreenfootImage[] images, Point offset) throws InvalidArgumentException {
        if (images == null || images.length == 0) {
            throw new InvalidArgumentException(new String[]{"images must be an array with at least one element"});
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