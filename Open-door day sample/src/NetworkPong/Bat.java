import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

import static greenfoot.Greenfoot.isKeyDown;

/**
 * Diese Klasse stellt einen Spieler (Schläger) dar.
 *
 */
public class Bat extends Actor{
    
    public Bat(){

    }
    
    public void act(){
        boolean up = shouldMoveUp(), down = shouldMoveDown();
        int x = getX();
        int y = getY();
        if(up && !down){
            y -= 3;
        }else if(!up && down){
            y += 3;
        }
        moveToIfPossible(x, y);
    }
    
    public void moveToIfPossible(int x, int y){
        if(isInWorld(x, y)){
            setLocation(x, y);
        }
    }
    
    private boolean isInWorld(int x, int y){
        World w = getWorld();
        return !(x < 0 || y < 0 || x > w.getWidth() || y > w.getHeight());
    }
    
    private boolean shouldMoveUp(){
        return isKeyDown("w") || isKeyDown("up");
    }
    
    private boolean shouldMoveDown(){
         return isKeyDown("s") || isKeyDown("down");
    }
    
}