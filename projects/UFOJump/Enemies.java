import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Enemies extends Actor
{
    protected int originX;
    protected int originY;
    protected int score;
    protected int soundCounter = 0;
    private boolean killed = false;
    private boolean playedSound = false;
    
    /**
     * Act - do whatever the Enemies wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        soundCounter++;
    }   
    
    public void setOriginX(int x){
        this.originX = x;
    } 
    
    public void die(){
        killed = true;
        Game game = (Game) getWorld();
        try{
        game.addScore(getScore());
    } catch (Exception e){
    System.out.println("f");
    }
        
    }
    
    public boolean isKilled(){
       return this.killed; 
    }
    
    public int getScore(){
        return this.score;
    }
    
    public boolean isVisibleOnLevel(){
        Game game = (Game) getWorld();
        if(getX() > game.getPostponement() && getX() < game.getPostponement() + game.getWidth())
            return true;
        return false;
    }
    
    public int getSoundCounter(){
        return this.soundCounter;
    }
    
    public boolean soundAlreadyPlayed(){
        return this.playedSound;
    }
    
    public void playedSound(){
        playedSound = true;
    }
}
