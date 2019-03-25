package de.greenfootdevz.fassspiel.util;

import greenfoot.GreenfootImage;

public class GreenfootImageExtended extends GreenfootImage {

    private String filename;

    public GreenfootImageExtended(GreenfootImage img, String filename) throws IllegalArgumentException {
        super(img);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
