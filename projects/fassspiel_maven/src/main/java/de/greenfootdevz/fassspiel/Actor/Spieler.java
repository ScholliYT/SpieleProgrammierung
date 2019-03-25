package de.greenfootdevz.fassspiel.Actor;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Spieler extends Actor {
    private String key;
    private String name;
    private GreenfootImage characterImage;

    public Spieler(String taste, String name, GreenfootImage characterImage) {
        this.key = taste;
        this.name = name;
        this.characterImage = characterImage;

        setImage(characterImage);
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void act() {
        // Add your action code here.
    }
}
