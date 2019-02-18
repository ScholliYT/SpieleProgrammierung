package de.greenfootdevz.charactereditor.Actor;

import greenfoot.Actor;

public class Arrow extends Actor {

    public Arrow(boolean isLeft) {
        if(isLeft) {
            setImage("LeftArrow.png");
        }else {
            setImage("RightArrow.png");
        }
    }

    public void act() {

    }
}
