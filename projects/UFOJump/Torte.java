import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Torte here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Torte extends Objects
{
    private boolean collected;
    private int value;
    
    private final int IMAGEFREQUENCE = 10;
    
    public Torte(int value){
        imageCount = 0;
        collected = false;
        this.value = value;
    }
    
    public void act(){
        imageCount++;
        if(imageCount <= 1){
            setImage("munze-1.png");
        }
        if(imageCount == 1 * IMAGEFREQUENCE + 1){
            setImage("munze-2.png");
        }
        if(imageCount == 2 * IMAGEFREQUENCE + 1){
            setImage("munze-3.png");
        }
        if(imageCount == 3 * IMAGEFREQUENCE + 1){
            setImage("munze-4.png");
        }
        if(imageCount == 4 * IMAGEFREQUENCE + 1){
            setImage("munze-5.png");
        }
        if(imageCount == 5 * IMAGEFREQUENCE + 1){
            setImage("munze-6.png");
        }
        if(imageCount == 6 * IMAGEFREQUENCE + 1){
            setImage("munze-7.png");
        }
        if(imageCount == 7 * IMAGEFREQUENCE + 1){
            setImage("munze-8.png");
        }
        if(imageCount == 8 * IMAGEFREQUENCE + 1){
            setImage("munze-9.png");
        }
        if(imageCount == 9 * IMAGEFREQUENCE + 1){
            setImage("munze-10.png");
        }
        if(imageCount == 10 * IMAGEFREQUENCE + 1){
            setImage("munze-11.png");
        }
        if(imageCount == 11 * IMAGEFREQUENCE + 1){
            setImage("munze-12.png");
            imageCount = 0;
        }
        
        if(collected){
            if(getY() > -20)
                //setLocation(getX(), getY() - 8);
                setLocation(-12, -12);
        }
    }    
    
    public void getCollected(){
        collected = true;
        this.value = 0;
        Greenfoot.playSound("coin.wav");
    }
    
    public int getValue(){
        return this.value;
    }
}
