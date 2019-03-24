package de.greenfootdevz.fassspiel.Actor;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Fass extends Actor {
    private final int geschw;
    private int score;
    private int accelleration;
    private Spieler spieler;
    private boolean isFalling;

    public Fass(Spieler spieler) {
        geschw = 1;
        score = 0;
        accelleration = 0;
        this.isFalling = true;
        this.spieler = spieler;
    }

    public void act() {
        if (isFalling) {
            accelleration++;
            score++;
            if (isTouching(Spieler.class)) {
                isFalling = false;
                score = 0;
                showScore();
            } else {
                setLocation(getX(), getY() + geschw * accelleration / 2); // non linear fall

                if (Greenfoot.isKeyDown(spieler.getKey())) {
                    isFalling = false;
                    showScore();
                }
            }
        }
    }

    // Displays score of the player above his head
    public void showScore() {
        this.getWorld().showText("" + score, spieler.getX(), this.getWorld().getHeight() / 2);

    }
}
