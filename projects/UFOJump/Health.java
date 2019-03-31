import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Health here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Health extends Actor
{
    private int health = 7;

    public Health(){
        this.setImage(new GreenfootImage("✯✯✯✯✯✯✯", 30, Color.GREEN, new Color(0,0,0,0)));
        //this.setImage(new GreenfootImage("", 30, Color.RED, new Color(0,0,0,0)));
    }

    public void act(){
        // Add your action code here.
    }   
    
    public void die(){
        this.health --;
        this.getImage().clear();
        String str = "";        
        for(int j = 0; j < health; j++){
            str += "✯";
        }
        this.setImage(new GreenfootImage(str, 30, Color.GREEN, new Color(0,0,0,0)));
    }
}
