import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse ClientMovedActor.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class ClientMovedActor extends Actor
{
    /**
     * Act - tut, was auch immer ClientMovedActor tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
     */
   public void act(){
        if(Greenfoot.isKeyDown("W")){
            move(1);
        }else if(Greenfoot.isKeyDown("S")){
            move(-1);
        }
        if(Greenfoot.isKeyDown("A")){
            turn(1);
        }else if(Greenfoot.isKeyDown("D")){
            turn(-1);
        }
    }     
}