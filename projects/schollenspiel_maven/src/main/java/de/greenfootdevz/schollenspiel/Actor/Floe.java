package de.greenfootdevz.schollenspiel.Actor;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

import java.util.Random;

public class Floe extends Actor {
    private boolean passAble;
    private int size;

    public Floe(boolean passAble) {
        this.passAble = passAble;
        Random rnd = new Random();
        this.size = 80;
        this.colorSetup(Color.WHITE);
    }

    public Floe(boolean passAble, int size) {
        this.passAble = passAble;
        this.size = size;
        this.colorSetup(Color.WHITE);
    }

    public void act() {
        if (!this.passAble)
            this.removeTouching(Player.class);
    }

    public void colorSetup(Color color) {
        GreenfootImage image = new GreenfootImage(size - 10, size - 10);
        image.setColor(color);
        image.fillOval(0, 0, size - 11, size - 11);
        image.fill();
        this.setImage(image);
    }

    public void paths(int x, int y, Color color) {
        GreenfootImage image = new GreenfootImage(x, y);
        image.setColor(color);
        image.fill();
        this.setImage(image);
    }

    public void setSate(boolean passAble) {
        this.passAble = passAble;
    }

    public boolean isTouching() {
        return this.isTouching(Player.class);
    }
}
