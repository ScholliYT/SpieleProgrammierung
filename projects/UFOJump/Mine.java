import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tretmine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mine extends Enemies
{
    /**
     * Act - do whatever the Tretmine wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(isTouching(Player.class)){
            if(isTouching(Clone.class)){
                removeTouching(Clone.class);
            }else{
                Enemies e=new Enemies();
                e.die();
            }

        }
    }    
}
