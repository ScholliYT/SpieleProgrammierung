import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Basey here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Worm extends Enemies
{
    private int movementCount;
    private int speed;
    private GreenfootSound sound = new GreenfootSound("basey.wav");
    private final int IMAGEFREQUENCE = 7;

    public Worm(int x, int speed){
        this.speed = -1*speed;
        originX = x;
        score = 30;       
    }

    public void act() 
    {

        if(isVisibleOnLevel() && !soundAlreadyPlayed()){
            sound.play();
            playedSound();
        }
        
        if(!isKilled()){                   
                setLocation(getX() + speed, getY());
        }else{
            if(getY() < 750)
                setLocation(getX(), getY() + 10);

            if(isVisibleOnLevel()){
                System.out.println("visible");
            }else{
                System.out.println("not visible");
            }
        }
    }
}
