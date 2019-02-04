package de.greenfootdevz.muenzspiel.World;

import de.greenfootdevz.muenzspiel.Actor.Coin;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Space extends World
{
    private int anzlCoins=60;
    private int time=30;
    ;
    /**
     * 
     */
    public Space()
    {    
        super(960, 620, 1);

        createCoins();
        //addObject(new Counter());
    }

    /**
     */
    public void createCoins()
    {
        for (int i=0;i<=anzlCoins;i++){
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            int values=3;
            int z=Greenfoot.getRandomNumber(10);
            if(z<=5){
            values=1;
            } else if(z<=9){
            values=2;
            }
            
            Coin c=new Coin(28, Color.CYAN, this, z);
            addObject(c,x,y);
            if(c.isTouching()){
                removeObject(c);
                i--;
            }           
        }
    }
    
    
    
    public void act(){
       
    }
}