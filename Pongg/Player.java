import greenfoot.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private boolean moved;
    private int which;
    /**
     * Constructor for objects of class Player 
     */
    public Player(int num)
    {
        turn(-90);
        moved = false;
        this.which = num;
    }

    public void act(){
        letsMove();
    }

    public boolean getMoved(){return moved;}

    public void setMoved(boolean n){moved = n;}

    private void letsMove(){
        if(which ==3){
            if (Greenfoot.isKeyDown("up")) 
            {
                move(3);
                setMoved(true); 
            }
            else if(Greenfoot.isKeyDown("down")) 
            {
                move(-3);
                setMoved(true);
            } else { setMoved(false);}}
        if(which ==1){
            if (Greenfoot.isKeyDown("x")) 
            {
                move(3);
                setMoved(true); 
            }
            else if(Greenfoot.isKeyDown("y")) 
            {
                move(-3);
                setMoved(true);
            } else { setMoved(false);}}
        if(which ==2){
            if (Greenfoot.isKeyDown("q")) 
            {
                move(3);
                setMoved(true); 
            }
            else if(Greenfoot.isKeyDown("w")) 
            {
                move(-3);
                setMoved(true);
            } else { setMoved(false);}}
        if(which ==0){
            if (Greenfoot.isKeyDown("o")) 
            {
                move(-3);
                setMoved(true); 
            }
            else if(Greenfoot.isKeyDown("p")) 
            {
                move(3);
                setMoved(true);
            } else { setMoved(false);}}

    }
}

