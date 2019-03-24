package de.greenfootdevz.fassspiel.Actor;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Spieler extends Actor {
    private String key;
    private String name;

    public Spieler(String taste, String name) {
        this.key = taste;
        this.name = name;
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
