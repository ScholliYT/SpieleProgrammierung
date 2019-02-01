package de.greenfootdevz.charactereditor.Network;

import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Client zum Host gesendet wird.
 */
public class PongClientData implements Serializable{
	
	private static final long serialVersionUID = -6243741163353349874L;
	
	private final int x;
	private final int y;
	
	public PongClientData(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}