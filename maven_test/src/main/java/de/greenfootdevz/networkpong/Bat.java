package de.greenfootdevz.networkpong;

import greenfoot.*;

public class Bat extends Actor {



  @Override
  public void act() {
    if (Greenfoot.isKeyDown("up")) {
      setLocation(getX(), getY() - 1);
    } else if (Greenfoot.isKeyDown("down")) {
      setLocation(getX(), getY() + 1);
    }
    if (Greenfoot.isKeyDown("left")) {
      setLocation(getX() - 1, getY());
    } else if (Greenfoot.isKeyDown("right")) {
      setLocation(getX() + 1, getY());
    }
  }

}
