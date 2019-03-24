package de.greenfootdevz.charactereditor.actor;

import de.greenfootdevz.charactereditor.util.ISaveCharacter;
import de.greenfootdevz.charactereditor.world.CharacterEditorWorld;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class PepePls extends Actor {

    private final String PIC_PREFIX = "pepePls/pepePls_";
    private final int LAST_STATE = 106;
    private final ISaveCharacter saveCharacter;

    private int state, width, height;

    public PepePls(int width, int height, ISaveCharacter saveCharacter) {
        this.width = width;
        this.height = height;
        this.saveCharacter = saveCharacter;
        this.state = 0;
    }

    public PepePls(ISaveCharacter saveCharacter) {
        this(100, 100, saveCharacter);
    }

    @Override
    public void act() {
        update();
        if (Greenfoot.mouseClicked(this)) {
            saveCharacter.saveCharacter();
        }
    }

    private void update() {
        setImage(PIC_PREFIX + state + ".png");
        getImage().scale(width, height);
        state = (state + 1) % LAST_STATE;
    }

}