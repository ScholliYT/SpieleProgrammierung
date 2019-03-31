import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enterprise here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enterprise extends Enemies
{
    private int movementCount, stayCount;
    private boolean reachTop;
    private boolean firstUp;
    private boolean stay;
    private GreenfootSound sound = new GreenfootSound("fly.wav");
    private GreenfootSound shoot = new GreenfootSound("laser-sound.mp3");
    private final int IMAGEFREQUENCE = 15;
    private final int WAIT = 20;

    /**
     * Act - do whatever the Enterprise wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Enterprise(int x, int y, boolean firstUp){
        movementCount = 0;
        stayCount = 0;
        reachTop = false;
        stay = false;
        originX = x;
        originY = y;
        score = 20;

        this.firstUp = firstUp;
    }

    public void act() 
    {
        int random= Greenfoot.getRandomNumber(10000);
        if(random>9998){
            shoot.play();
        getWorld().addObject(new Bullet(), getX(), getY());
        }
        if(!isKilled()){
           
            if(firstUp){
                if(stay){
                    stayCount ++;
                }else{
                    if(!reachTop)
                        movementCount ++;
                    else
                        movementCount --;
                        
                    if(movementCount == 75 || movementCount == 0)
                        changeReachTop();
                }
            }else{
                if(stay){
                    stayCount ++;
                }else{
                    if(!reachTop)
                        movementCount --;
                    else
                        movementCount ++;
                        
                    if(movementCount == -75 || movementCount == 0)
                        changeReachTop();
                }
            }
            
            if(stayCount == WAIT){
                stayCount = 0;
                stay = false;
            }

            setLocation(originX-movementCount, originY - movementCount);
        }else{
            if(getY() < 750)
                //setLocation(getX(), getY() + 10);
                setLocation(getX()+10, getY() + 10);
        }

        if(isVisibleOnLevel() && !soundAlreadyPlayed()){
            sound.play();
            playedSound();
        }

    }

    private void changeReachTop(){
        if(reachTop)
            reachTop = false;
        else
            reachTop = true;

        stay = true;
    }
}  
