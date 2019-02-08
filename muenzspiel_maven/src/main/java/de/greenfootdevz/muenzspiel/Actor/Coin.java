package de.greenfootdevz.muenzspiel.Actor;

import com.sun.media.sound.InvalidDataException;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Actor {

    private final Color playerColor;
    private final int value;

    private boolean clicked = false;
    private GreenfootImage[] image = new GreenfootImage[6];

    public boolean isClicked() {
        return clicked;
    }

    public int getValue() {
        return value;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public Coin(Color playerColor, int value) throws InvalidDataException {
        this.playerColor = playerColor;
        this.value = value;

        // Get Images
        image[0] = new GreenfootImage("muenzeEinsGelb.png");
        image[1] = new GreenfootImage("muenzeZweiGelb.png");
        image[2] = new GreenfootImage("muenzeDreiGelb.png");
        image[3] = new GreenfootImage("muenzeEinsRot.png");
        image[4] = new GreenfootImage("muenzeZweiRot.png");
        image[5] = new GreenfootImage("muenzeDreiRot.png");

        // set initial image
        switch (this.value) {
            case 1:
                setImage(image[0]);
                break;
            case 2:
                setImage(image[1]);
                break;
            case 3:
                setImage(image[2]);
                break;
            default:
                throw new InvalidDataException("value: " + value);
        }
    }

    public void act() {
        MouseInfo m = Greenfoot.getMouseInfo();
        if (m != null && Greenfoot.mouseClicked(null)) {
            m = Greenfoot.getMouseInfo();
            if (m.getActor() == this) {
                if (isClicked()) {
                    switch (value) {
                        case 2:
                            setImage(image[1]);
                            break;
                        case 3:
                            setImage(image[2]);
                            break;
                        default:
                            setImage(image[0]);
                            break;
                    }
                    clicked = false;
                } else {
                    switch (value) {
                        case 2:
                            setImage(image[4]);
                            break;
                        case 3:
                            setImage(image[5]);
                            break;
                        default:
                            setImage(image[3]);
                            break;
                    }
                    clicked = true;
                }

            }
        }
    }

    public boolean isTouchingOtherCoin() {
        return this.isTouching(Coin.class);
    }
}
