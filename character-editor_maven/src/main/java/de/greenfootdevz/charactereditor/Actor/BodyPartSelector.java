package de.greenfootdevz.charactereditor.Actor;

import greenfoot.World;

public class BodyPartSelector {
    private final BodyPart bodyPart;
    private final Arrow rightArrow;
    private final Arrow leftArrow;

    public BodyPartSelector(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        this.leftArrow = new Arrow(true, () -> bodyPart.previous());
        this.rightArrow = new Arrow(false, () -> bodyPart.next());
    }

    public void addObjects(World world, int posX, int posY) {
        world.addObject(bodyPart, posX, posY);
        world.addObject(leftArrow, posX - (bodyPart.getImage().getWidth() /2 + 10), posY);
        world.addObject(rightArrow, posX + (bodyPart.getImage().getWidth() /2 + 10), posY);
    }
}
