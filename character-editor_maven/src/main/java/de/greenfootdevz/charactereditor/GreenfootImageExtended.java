package de.greenfootdevz.charactereditor;

import greenfoot.GreenfootImage;

public class GreenfootImageExtended extends GreenfootImage {

    private String filename;

    public GreenfootImageExtended(String filename) throws IllegalArgumentException {
        super(filename);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
