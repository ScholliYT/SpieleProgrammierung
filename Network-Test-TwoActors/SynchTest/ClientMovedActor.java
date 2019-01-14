import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class ClientMovedActor extends Actor{
    
    public ClientMovedActor(){
        setImage(new ExtendedGreenfootImage("man01.png"));
    }
    
    public void act(){
        if(Greenfoot.isKeyDown("W")){
            move(1);
        }else if(Greenfoot.isKeyDown("S")){
            move(-1);
        }
        if(Greenfoot.isKeyDown("A")){
            turn(-1);
        }else if(Greenfoot.isKeyDown("D")){
            turn(1);
        }
    }    
}