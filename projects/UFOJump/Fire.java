import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fire here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fire extends Objects
{
    /**
     * Act - do whatever the Fire wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public final int SPEED=2;
    public int direction;
    public long startTime=System.currentTimeMillis();
    
    public Fire(int direction){
        this.direction=direction;
        }

    public void act() 
    {
        long cTime=System.currentTimeMillis();
        long flTime=cTime-startTime;
        //while(flTime<20000000)
        {
            cTime=System.currentTimeMillis();
            flTime=cTime-startTime;
            System.out.println(System.currentTimeMillis()-startTime<2000);
            if(direction==1){
                this.setLocation(getX()-SPEED, getY());
            }else{
                setLocation(getX()+SPEED,getY());
            }
            
            if(isTouching(Enemies.class)){
                removeTouching(Enemies.class);
                //removeTouching(Objects.class);
                startTime=0;
                cTime=0;
                flTime=0;
                getWorld().removeObject(this);
            }
            
            if(startTime-System.currentTimeMillis()>=20000000){
                startTime=0;
                cTime=0;
                flTime=0;
            getWorld().removeObject(this);
            }
        }
    }    
}
