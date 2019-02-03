import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 800, 1); 
        populate();
    }
    
    public void populate(){
        for (int i=0;i<=3;i++){
            Spieler spieler=new Spieler("control", "Spieler 1") ;
        addObject(spieler,75+150*0,getHeight()-20);
        addObject(new Fass(spieler),75+150*0,20 );
        
        Spieler spieler2=new Spieler("space", "Spieler 2") ;
        addObject(spieler2,75+150*1,getHeight()-20);
        addObject(new Fass(spieler2),75+150*1,20 );
        
        Spieler spieler3=new Spieler("shift", "Spieler 3") ;
        addObject(spieler3,75+150*2,getHeight()-20);
        addObject(new Fass(spieler3),75+150*2,20 );
        
        Spieler spieler4=new Spieler("enter", "Spieler 4") ;
        addObject(spieler4,75+150*3,getHeight()-20);
        addObject(new Fass(spieler4),75+150*3,20 );
    }
    
        
    
    }
    
}
