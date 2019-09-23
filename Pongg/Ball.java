import greenfoot.*;
import java.util.*;
/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball extends SmoothMover
{
    // instance variables - replace the example below with your own
    private double speed = 1; // geschwindigkeit

    /**
     * Constructor for objects of class Ball
     */
    public Ball()
    {
        //int k = getRandoo();
        super( new Vector( Greenfoot.getRandomNumber(360), 1));

    }

    public void act(){
        //move(speed);
        this.touch(); // speed ändern
        speed();
        move();
        see();// Wand wird berührt
    }

    private int getRandoo(){
        int randomnumb;
        int rand = Greenfoot.getRandomNumber(12);
        return  rand*30;    
    }

    private void touch(){
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null){
            if(player.getMoved()){
                speed = speed*1.05;}
            else{
                speed *= 0.95;
            }
            if(player.getRotation()==270 || player.getRotation()==90){
                getVector().revertHorizontal();}
            else{
                getVector().revertVertical();
            }
        }

    }

    private void see(){
        if (exactX >= getWorld().getWidth()) {
            velocity.revertHorizontal();
            if(((MyWorld) getWorld()).getSco(3)>0){
                ((MyWorld) getWorld()).newScore(3);}

        }
        if (exactX < 0) {
            velocity.revertHorizontal();
            if(((MyWorld) getWorld()).getSco(1)>0){
                ((MyWorld) getWorld()).newScore(1);}

        }
        if (exactY >= getWorld().getHeight()) {
            velocity.revertVertical();
            if(((MyWorld) getWorld()).getSco(2)>0){
                ((MyWorld) getWorld()).newScore(2);}

        }
        if (exactY < 0) {
            velocity.revertVertical();
            if(((MyWorld) getWorld()).getSco(0)>0){
                ((MyWorld) getWorld()).newScore(0);}
        }

    }

    private void speed(){
        getVector().setLength(speed);
    }
}
