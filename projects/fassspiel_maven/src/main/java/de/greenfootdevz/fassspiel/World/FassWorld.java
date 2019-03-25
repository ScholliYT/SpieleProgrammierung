package de.greenfootdevz.fassspiel.World;

import de.greenfootdevz.fassspiel.Actor.*;
import de.greenfootdevz.fassspiel.util.GreenfootImageExtended;
import de.greenfootdevz.fassspiel.util.ImageLoader;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class FassWorld extends World {

    public FassWorld() {
        super(600, 1000, 1);

        populate();
    }

    public void populate() {
        GreenfootImage characterImage1 = getCharacterImage();
        Spieler spieler1 = new Spieler("control", "Spieler 1", characterImage1);
        addObject(spieler1, 75 + 150 * 0, getHeight() - spieler1.getImage().getHeight() / 2 - 5);
        Fass fass1 = new Fass(spieler1);
        addObject(fass1, 75 + 150 * 0, 5 + fass1.getImage().getHeight() / 2);

        GreenfootImage characterImage2 = getCharacterImage();
        Spieler spieler2 = new Spieler("space", "Spieler 2", characterImage2);
        addObject(spieler2, 75 + 150 * 1, getHeight() - spieler2.getImage().getHeight() / 2 - 5);
        Fass fass2 = new Fass(spieler2);
        addObject(new Fass(spieler2), 75 + 150 * 1, 5 + fass2.getImage().getHeight() / 2);

        GreenfootImage characterImage3 = getCharacterImage();
        Spieler spieler3 = new Spieler("enter", "Spieler 3", characterImage3);
        addObject(spieler3, 75 + 150 * 2, getHeight() - spieler3.getImage().getHeight() / 2 - 5);
        Fass fass3 = new Fass(spieler3);
        addObject(new Fass(spieler3), 75 + 150 * 2, 5 + fass3.getImage().getHeight() / 2);

        GreenfootImage characterImage4 = getCharacterImage();
        Spieler spieler4 = new Spieler("up", "Spieler 4", characterImage4);
        addObject(spieler4, 75 + 150 * 3, getHeight() - spieler4.getImage().getHeight() / 2 - 5);
        Fass fass4 = new Fass(spieler4);
        addObject(new Fass(spieler4), 75 + 150 * 3, 5 + fass4.getImage().getHeight() / 2);
    }

    private GreenfootImage getCharacterImage() {
        GreenfootImageExtended img = ImageLoader.getCharacterImage();

        if(img == null) {
            img = ImageLoader.getDefaultCharacterImage();
        }
        img.scale(100, 250);
        return img;
    }
}
