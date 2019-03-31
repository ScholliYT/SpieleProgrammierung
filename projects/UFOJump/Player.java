import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private double exactX = 0; // exact X-coordinates
    private double exactY = 0; // exact Y-coordinates
    private double velocityX; // in pixels per act() call
    private double velocityY; // in pixels per act() call
    private int score;
    private int health = 10;
    
    final double JUMPHEIGHT = 4.5;
    
    public void move()
    {
        setLocation(exactX + velocityX, exactY + velocityY);
        Game game = (Game) getWorld();
        if(getOneIntersectingObject(Enemies.class) != null){
            if(checkEnemyCollision()){
                game.looseLife();
            }
        }
        if(getOneIntersectingObject(Torte.class) != null){
            game.collectTorte((Torte) getOneIntersectingObject(Torte.class));
        }
        if(getY() - getHeight() > getWorld().getHeight()){
            game.looseLife();
        }
        if(getOneIntersectingObject(Kaktus.class) != null){
            game.looseLife();
        }
        if(getOneIntersectingObject(Bullet.class) != null){
            game.looseLife();
        }
        if(getOneIntersectingObject(Finish.class) != null){
            game.finishedGame();
        }
    }
     
    public void setLocation(int x, int y) {
        exactX = x;
        exactY = y;
        super.setLocation(x, y);
    }
    
    public void setLocation(double x, double y)
    {
        this.exactX = x;
        this.exactY = y;
        super.setLocation((int) x, (int) y);
    }
     
    public double getExactX(){
        return exactX;
    }
    
    public double getExactY(){
        return exactY;
    }
    
    public void setX(double x)
    {
        this.exactX = x;
    }
    
    public void setY(double y)
    {
        this.exactY = y;
    }
    
    public double getVelocityX(){
        return velocityX;
    }
    
    public double getVelocityY(){
        return velocityY;
    }
    
    public void setVelocityX(double x)
    {
        velocityX = x;
    }
    
    public void setVelocityY(double y)
    {
        velocityY = y;
    }

    public int getHeight()
    {
        return getImage().getHeight();
    }
    
    public int getWidth()
    {
        return getImage().getWidth();
    }
    
    public boolean atBeginningOfWorld()
    {
        Game game = (Game) getWorld();
        if(game.worldIsLeft())
            return true;
        else
            return false;
    }
    
    public boolean atEndingOfWorld()
    {
        Game game = (Game) getWorld();
        if(game.getPostponement() == 0)
            return true;
        else
            return false;
    }
    
    public boolean atLeftEdgeOfWorld()
    {
        if(getX() < 200)
            return true;
        else
            return false;
    }
    
    public boolean atRightEdgeOfWorld()
    {
        if(getX() > getWorld().getWidth() - 200)
            return true;
        else
            return false;
    }
    
    private boolean checkEnemyCollision(){
        Enemies enemy = (Enemies)getOneObjectAtOffset(0, getHeight() / 2 + 5, Enemies.class);
        Enemies enemyRightTop = (Enemies)getOneObjectAtOffset(getWidth() / 2, getHeight() / 2 + 5, Enemies.class);
        Enemies enemyLeftTop = (Enemies)getOneObjectAtOffset(getWidth() / -2, getHeight() / 2 + 5, Enemies.class);
        if(enemy != null || enemyRightTop != null || enemyLeftTop != null){
            if(enemy != null)
                enemy.die();
            if(enemyRightTop != null)
                enemyRightTop.die();
            if(enemyLeftTop != null)
                enemyLeftTop.die();
            
            setVelocityY(-JUMPHEIGHT);
        }
        
        enemy = (Enemies)getOneObjectAtOffset(0, getHeight() / -2, Enemies.class);
        if(enemy != null){
            return true;
        }
        
        enemy = (Enemies)getOneObjectAtOffset(getWidth() / 2, 0, Enemies.class);
        if(enemy != null){
            return true;
        }
        
        enemy = (Enemies)getOneObjectAtOffset(getWidth() / -2, 0, Enemies.class);
        if(enemy != null){
            return true;
        }
        return false;
    }
}
