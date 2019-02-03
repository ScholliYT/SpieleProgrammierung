import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fass extends Actor
{
    /**
     * Act - do whatever the Fass wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private int geschw;
    private int score;
    private int i;
    private Spieler spieler;
    public Fass(Spieler spieler){
        geschw=1;
        score=0;
        i=0;
        this.spieler=spieler;
    }

    public void act() 
    {
        i++;
        score++;
        if (!isTouching(Spieler.class)){
            setLocation(getX(), getY()+geschw*i/2);

            if(Greenfoot.isKeyDown(spieler.getTaste())){
                auswertung();
                geschw=0;
                return;
            }

        }
        
        if(isTouching(Spieler.class)){
            score=0;
            auswertung();
            Greenfoot.stop();
            
        }

    }   

    public void auswertung(){
        this.getWorld().showText("Score of"+spieler.getName() +"is" +score, this.getWorld().getWidth()/2,this.getWorld().getHeight()/2 );
        
    }
}
