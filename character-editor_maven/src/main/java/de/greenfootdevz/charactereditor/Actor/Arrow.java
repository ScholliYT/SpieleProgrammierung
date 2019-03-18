package de.greenfootdevz.charactereditor.Actor;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Arrow extends Actor {

    private final IClickEvent onClick;

    public Arrow(boolean isLeft, IClickEvent onClick) {
        if (isLeft) {
            setImage("controls/leftarrow.png");
        } else {
            setImage("controls/rightarrow.png");
        }
        this.onClick = onClick;

    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            onClick.execute();
        }
    }
}
