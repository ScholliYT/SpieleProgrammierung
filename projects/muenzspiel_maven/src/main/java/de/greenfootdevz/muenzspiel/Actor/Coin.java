package de.greenfootdevz.muenzspiel.Actor;

import de.greenfootdevz.muenzspiel.World.Space;
import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Coin extends Actor{
	
	private final int value;
	
	private boolean clicked = false;
	private GreenfootImage[] image = new GreenfootImage[6];
	private Space s;
	
	public boolean isClicked() {
		return clicked;
	}
	
	public int getValue() {
		return value;
	}
	
	public Coin(int value) throws IllegalArgumentException{
		this.value = value;
		// Get Images
		image[0] = new GreenfootImage("muenzeEinsGelb.png");
		image[1] = new GreenfootImage("muenzeZweiGelb.png");
		image[2] = new GreenfootImage("muenzeDreiGelb.png");
		image[3] = new GreenfootImage("muenzeEinsRot.png");
		image[4] = new GreenfootImage("muenzeZweiRot.png");
		image[5] = new GreenfootImage("muenzeDreiRot.png");

		// set initial image
		switch (this.value) {
		case 1:
			setImage(image[0]);
			break;
		case 2:
			setImage(image[1]);
			break;
		case 3:
			setImage(image[2]);
			break;
		default:
			throw new IllegalArgumentException("value: " + value);
		}
	}
	
	public void act(){
		if(s == null) s = (Space) getWorld();
		
		if(Greenfoot.mouseClicked(this)){
			s.removeObject(this);
			s.addCollected(this);
		}
	}
	
	public boolean isTouchingOtherCoin() {
		return this.isTouching(Coin.class);
	}
}