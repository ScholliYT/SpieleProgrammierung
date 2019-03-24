package de.greenfootdevz.charactereditor.actor;

import greenfoot.World;

public class BodyPartSelector {
    private final BodyPart bodyPartPrev;
    private final BodyPart bodyPart;
    private final Arrow rightArrow;
    private final Arrow leftArrow;

    public BodyPartSelector(BodyPart bodyPartprev, BodyPart bodyPart) {
        this.bodyPartPrev = bodyPartprev;
        this.bodyPart = bodyPart;
        this.leftArrow = new Arrow(true, () -> {
            bodyPartprev.previous();
            bodyPart.previous();
        });
        this.rightArrow = new Arrow(false, () -> {
            bodyPartprev.next();
            bodyPart.next();
        });
    }

    public void addObjects(World world, int posX, int posY) {
        world.addObject(bodyPartPrev, posX, posY);
        world.addObject(leftArrow, posX - (80), posY);
        world.addObject(rightArrow, posX + (80), posY);
    }
}
