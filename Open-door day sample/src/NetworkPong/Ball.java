import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)
import java.util.*;

public class Ball extends Actor{
    
    public Ball(){
    }
    
    public <A> Actor getIntesecting(Class<A> cl){
       return getOneIntersectingObject(cl);
    }
    
}