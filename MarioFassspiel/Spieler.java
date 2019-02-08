import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spieler here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spieler extends Actor
{
    /**
     * Act - do whatever the Spieler wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private String taste;
    private String name;
    public Spieler(String taste, String name){
        this.taste=taste; 
        this.name=name;
    }
    public String getTaste(){
    return taste;
    }
    public String getName(){
    return name;
    }
    public void act() 
    {
        // Add your action code here.
    }    
}
