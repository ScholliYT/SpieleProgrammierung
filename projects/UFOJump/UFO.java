import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class UFO here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UFO extends Player
{
    private int direction;
    private int imageCount;
    private boolean fast;
    private Game world;
    private int stepcounter = 0;
    private boolean fastDown = false;

    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int IMAGEFREQUENCE = 5;
    private final double SPEED = 0.75;
    private final double FASTSPEED = 1.4;
    private final double GRAVITY = 0.15;

    private  double jumpCounter=0;
    private long jumptimeStart=0;
    private long clonetimeStart=0;
    private long firetimeStart=0;
    private long minetimeStart=0;
    private boolean isInUfo=false;  

    public UFO(){
        imageCount = 0;
        fast = false;
        this.world = getWorldOfType(Game.class);
    }

    public UFO(double x, double y){
        imageCount = 0;
        setX(x);
        setY(y);
        fast = false;
        this.world = getWorldOfType(Game.class);
    }

    public void act() 
    {
        if(world == null)
            this.world = getWorldOfType(Game.class);

        if(this.world.lostLive()){
            if(getY() < this.world.getHeight() + getHeight())
                setLocation(getX(), getY() + 1);
            else
                this.world.continueAfterLoosingLife();
        }else{
            if(!this.world.isDead() && !this.world.hasWon()&& world.getScore()>0){
                if(!isInUfo()){
                    if (Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("left")){
                        direction = RIGHT;
                        moveRight();
                    }else if (Greenfoot.isKeyDown("left") && !Greenfoot.isKeyDown("right")){
                        direction = LEFT;
                        moveLeft();
                    }else{
                        setVelocityX(0.0); // ->stop moving
                    }
                    if (Greenfoot.isKeyDown("space")&& onEarth()||Greenfoot.isKeyDown("space")&&(jumpCounter==1 && System.currentTimeMillis()-jumptimeStart>500|| Greenfoot.isKeyDown("space")&& isTouching(GroundBlock.class))  ){
                        jumptimeStart=System.currentTimeMillis();
                        //jump();
                        setVelocityY(-JUMPHEIGHT);
                        jumpCounter++;
                        System.out.println(jumpCounter);
                    }
                    if(getVelocityY() == 0){
                        jumpCounter=0;
                        jumptimeStart=0;
                    }
                    if(Greenfoot.isKeyDown("shift"))
                        fast = true;
                    else
                        fast = false;

                    if (Greenfoot.isKeyDown("down") && !onEarth()){
                        //jump();
                        setVelocityY(getVelocityY() + GRAVITY * 5);
                        fastDown = true;
                    }else if (!onEarth()){//you need to find out when the actor is on the ground and when he is in the air;
                        setVelocityY(getVelocityY() + GRAVITY);
                    }
                    else {
                        setVelocityY(0);
                        if(fastDown && Greenfoot.isKeyDown("down")){
                            fastDown = false;
                            Greenfoot.playSound("schnell-runter.wav");
                        }
                    }
                    if (Greenfoot.isKeyDown("f")&&System.currentTimeMillis()-firetimeStart>15000){
                        firetimeStart=System.currentTimeMillis();
                        Fire fire=new Fire(direction);
                        getWorld().addObject(fire,getX(),getY());
                    }

                    if (Greenfoot.isKeyDown("c")&&System.currentTimeMillis()-clonetimeStart>15000){
                        clonetimeStart=System.currentTimeMillis();
                        Clone clone=new Clone();
                        getWorld().addObject(clone,getX(),getY());
                    }
                } else{
                    fast=true;
                    if (Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("left")){
                        direction = RIGHT;
                        moveRight();
                    }else if (Greenfoot.isKeyDown("left") && !Greenfoot.isKeyDown("right")){
                        direction = LEFT;
                        moveLeft();
                    }else if(Greenfoot.isKeyDown("up")){
                        moveTop();
                    }else if(Greenfoot.isKeyDown("down")){
                        moveDown();                         
                    }
                    else{
                         setVelocityX(0.0);
                                          
                    }
                    if (Greenfoot.isKeyDown("z")){
                        isInUfo=false;
                        setImage("alien0.png");
                    }
                    if (Greenfoot.isKeyDown("f")){                    
                        Fire fire=new Fire(direction);
                        getWorld().addObject(fire,getX(),getY());
                    }
                }

                if (Greenfoot.isKeyDown("t")&&System.currentTimeMillis()-minetimeStart>15000){
                    minetimeStart=System.currentTimeMillis();
                    Mine mine=new Mine();
                    getWorld().addObject(mine,getX()-20,getY());
                }


                if(checkCollisionX())
                    setVelocityX(0.0);

                if(atLeftEdgeOfWorld()){
                    world.moveAllObjects(1);
                }

                if(atRightEdgeOfWorld()){
                    world.moveAllObjects(-1);
                }

                if(isTouching(UFOObject.class)){
                    isInUfo=true;
                    setImage("ufo00.png");
                }

                move();
            }else{                
                try{
                    Thread.sleep(5000);
                }catch(Exception e){
                }
                Greenfoot.stop();
            }
        }
    }

    private void moveRight(){
        if(fast)
            setVelocityX(FASTSPEED);
        else
            setVelocityX(SPEED);
        moveleftright();

    }

    private void moveTop(){
        setLocation(getX(), getY()-SPEED);
    }
    
    private void moveDown(){
    setLocation(getX(),getY()+SPEED);
    }

    private void moveleftright(){
        imageCount++;
        if(getVelocityY() != 0 || checkCollisionX()){
            //setVelocityX(-SPEED);
            imageCount = 0;
            //setImage("luigi-left-1.png");
        }else{
            // if(imageCount <= 1){
            // setImage("luigi-left-1.png");
            // stepSound.play();
            // }
            // if(imageCount == 1 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-2.png");
            // }
            // if(imageCount == 2 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-3.png");
            // }
            // if(imageCount == 3 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-4.png");
            // }
            // if(imageCount == 4 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-5.png");
            // }
            // if(imageCount == 5 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-6.png");
            // }
            // if(imageCount == 6 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-7.png");
            // }
            // if(imageCount == 7 * IMAGEFREQUENCE + 1){
            // setImage("luigi-left-8.png");
            // imageCount = 0;
            // }
        }
    }

    private void moveLeft(){
        if(fast)
            setVelocityX(-FASTSPEED);
        else
            setVelocityX(-SPEED);
        moveleftright();

    }

    private boolean isInUfo(){
        return isInUfo;
    }

    private boolean checkCollisionX(){
        double velocityX = getVelocityX();
        int lookAtX = 0;
        if (velocityX < 0)
            lookAtX = (int) getWidth() / -2;
        else
            lookAtX = (int) getWidth() / 2;

        Actor actor = getOneObjectAtOffset(lookAtX, getHeight() / 4, Objects.class);
        if(actor != null){
            if (actor.getClass() == GroundBlock.class) {
                return true;
            }
        }
        return false;
    }

    private boolean onEarth(){
        double velocityY = getVelocityY();
        int lookAtY = 0;
        if (velocityY < 0)
            lookAtY = (int) getHeight() / -2;
        else
            lookAtY = (int) getHeight() / 2;

        Actor actor = getOneObjectAtOffset(getWidth() / 4, lookAtY, Objects.class);
        if (actor != null) {  
            return true;
        }
        return false;
    }

}
