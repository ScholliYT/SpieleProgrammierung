package de.greenfootdevz.networkpong.Actor;

import greenfoot.Actor;

public class Ball extends Actor{

    private double actualX, actualY;

    public Ball(){
    }
    

    public double getActualX() {
        return actualX;
    }

    public double getActualY() {
        return actualY;
    }
    
    public <A> Actor getIntesecting(Class<A> cl){
       return getOneIntersectingObject(cl);
    }


    public void setLocation(double x, double y){
        this.actualX = x;
        this.actualY = y;
        super.setLocation((int)x,(int)y);
    }

    @Override
    public void setLocation(int x, int y){
        this.setLocation(((Number) x).doubleValue(), ((Number) y).doubleValue());
    }

    
}