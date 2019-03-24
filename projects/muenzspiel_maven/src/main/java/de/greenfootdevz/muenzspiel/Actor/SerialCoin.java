package de.greenfootdevz.muenzspiel.Actor;

import java.io.Serializable;

public class SerialCoin implements Serializable{
	
	private static final long serialVersionUID = -5433681059874781666L;
	
	private int x, y, value;
	
	public SerialCoin(Coin c){
		this.x = c.getX();
		this.y = c.getY();
		this.value = c.getValue();
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getValue(){
		return value;
	}
	
	@Override
	public String toString(){
		return "[x=" + x + ", y=" + y + ", value=" + value + "]";
	}
	
}