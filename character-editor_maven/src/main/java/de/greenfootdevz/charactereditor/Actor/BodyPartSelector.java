package de.greenfootdevz.charactereditor.Actor;

import greenfoot.Actor;

public class BodyPartSelector {
    private final BodyPart bodyPart;
    private final Arrow rightArrow;
    private final Arrow leftArrow;

    public BodyPartSelector(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        this.leftArrow = new Arrow(true);
        this.rightArrow = new Arrow(false);
    }





}
