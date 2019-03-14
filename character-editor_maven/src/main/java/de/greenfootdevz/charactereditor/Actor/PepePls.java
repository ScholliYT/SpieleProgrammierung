package de.greenfootdevz.charactereditor.Actor;

import greenfoot.Actor;

public class PepePls extends Actor{
	
	private final String PIC_PREFIX = "pepePls/pepePls_";
	private final int LAST_STATE = 106;
	
	private int state, width, height;
	
	public PepePls(int width, int height){
		this.width = width;
		this.height = height;
		this.state = 0;
	}
	
	public PepePls(){
		this(100, 100);
	}
	
	@Override
	public void act(){
		update();
	}
	
	private void update(){
		setImage(PIC_PREFIX + state + ".png");
		getImage().scale(width, height);
		state = (state + 1) % LAST_STATE;
	}
	
}