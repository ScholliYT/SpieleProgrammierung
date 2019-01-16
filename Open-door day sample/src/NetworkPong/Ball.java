import greenfoot.Actor;

public class Ball extends Actor{
    
    public Ball(){
    }
    
    public <A> Actor getIntesecting(Class<A> cl){
       return getOneIntersectingObject(cl);
    }
    
}