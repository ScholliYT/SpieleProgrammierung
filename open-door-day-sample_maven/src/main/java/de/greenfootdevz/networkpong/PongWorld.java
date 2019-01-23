package de.greenfootdevz.networkpong;

import greenfoot.*;
import de.greenfootdevz.networkpong.Bat;

public class PongWorld extends World {

  /**
  * Constructor for PongWorld.
  */
  public PongWorld()
  {
    super(850, 600, 1);
    prepare();
  }

  /**
   * Prepare the world for the start of the program.
   * That is: create the initial objects and add them to the world.
   * Z-Index is based on order of addition (last to be added is on top)
   */
  private void prepare()
  {
    Bat actor = new Bat();
    addObject(actor, 100, 50);
  }

}
