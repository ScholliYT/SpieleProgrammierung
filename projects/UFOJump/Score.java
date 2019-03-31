import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.*;
/**
 * Write a description of class Score here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Score extends Actor
{
    private GreenfootImage bild = new GreenfootImage(100,50);
    private int score = 100000;

    public Score(){
        this.setImage(new GreenfootImage("Score: "+score, 20, Color.BLACK, new Color(0,0,0,0)));
    }

    public void act(){        

        if(score<0){
            score=0;
        }else{
            syncScore(-1);         
        }
        getScore();
    }

    public int getScore(){
        System.out.println("score "+score);
        return score;
    }

    public void syncScore(int score){
        this.score += score;
        this.getImage().clear();
        this.setImage(new GreenfootImage("Score: " + this.score, 20, Color.WHITE, new Color(0,0,0,0)));
    }
}
