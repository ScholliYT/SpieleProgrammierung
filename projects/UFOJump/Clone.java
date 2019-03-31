import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Clone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Clone extends Player
{
    /**
     * Act - do whatever the Clone wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int imageCount;
    private final double SPEED = 1.55+1;
    private final int IMAGEFREQUENCE = 5;
    public void act() 
    {
       imageCount++;
        setLocation(getX()+SPEED,getY());
        if(isTouching(Enemies.class)){
        getWorld().removeObject(this);
        
        }
        if(getVelocityY() != 0 ){ //|| checkCollisionX()
            //setVelocityX(SPEED);
            imageCount = 0;
            setImage("luigi-1.png");

        }else{
            // if(imageCount <= 1){
                // setImage("luigi-1.png");
                
            // }
            // if(imageCount == 1 * IMAGEFREQUENCE + 1){
                // setImage("luigi-2.png");
            // }
            // if(imageCount == 2 * IMAGEFREQUENCE + 1){
                // setImage("luigi-3.png");
            // }
            // if(imageCount == 3 * IMAGEFREQUENCE + 1){
                // setImage("luigi-4.png");
            // }
            // if(imageCount == 4 * IMAGEFREQUENCE + 1){
                // setImage("luigi-5.png");
            // }
            // if(imageCount == 5 * IMAGEFREQUENCE + 1){
                // setImage("luigi-6.png");
            // }
            // if(imageCount == 6 * IMAGEFREQUENCE + 1){
                // setImage("luigi-7.png");
            // }
            // if(imageCount == 7 * IMAGEFREQUENCE + 1){
                // setImage("luigi-8.png");
                // imageCount = 0;
            // }
        }
    }   
    
    // private boolean checkCollisionX(){
        // double velocityX = getVelocityX();
        // int lookAtX = 0;
        // if (velocityX < 0)
            // lookAtX = (int) getWidth() / -2;
        // else
            // lookAtX = (int) getWidth() / 2;

        // Actor actor = getOneObjectAtOffset(lookAtX, getHeight() / 4, Objects.class);
        // if(actor != null){
            // if (actor.getClass() == GroundBlock.class || actor.getClass() == Pipe.class) {
                // //Berührt ein Objekt der Klasse Objects
                // return true;
            // }
        // }
        // return false;
    // }
 }
