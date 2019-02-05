package de.greenfootdevz.networkpong.Actor;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

import static greenfoot.Greenfoot.isKeyDown;

/**
 * Diese Klasse stellt einen Spieler (Schläger) dar.
 *
 */
public class Bat extends Actor{

    private final int BAT_SPEED_Y = 8;

    public Bat(){

    }
    
    public void act(){
        boolean up = shouldMoveUp(), down = shouldMoveDown();
        int x = getX();
        int y = getY();
        if(up && !down){
            y -= BAT_SPEED_Y;
        }else if(!up && down){
            y += BAT_SPEED_Y;
        }
        moveToIfPossible(x, y);
    }
    
    public void moveToIfPossible(int x, int y){
        if(isInWorld(x, y)){
            setLocation(x, y);
        }
    }
    
    private boolean isInWorld(int x, int y){
        int playerHeight = getImage().getHeight();
        World w = getWorld();
        return !(x < 0 || y < playerHeight/2 || x > w.getWidth() || y > w.getHeight() - playerHeight/2);
    }
    
    private boolean shouldMoveUp(){
        return isKeyDown("w") || isKeyDown("up");
    }
    
    private boolean shouldMoveDown(){
         return isKeyDown("s") || isKeyDown("down");
    }
    
}