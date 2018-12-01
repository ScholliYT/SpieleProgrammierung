package synch;

import java.awt.Point;
import java.io.Serializable;

import client.ExtendedGreenfootImage;
import greenfoot.Actor;

public class ActorState implements Serializable{
	
	private static final long serialVersionUID = 8760631322861178939L;
	
	private transient Actor actor;
	
	private Point location;
	private int rotation;
	private String pic;
	
	private ActorState(Actor actor){
		this.actor = actor;
		this.update();
	}
	
	public int getX(){
		return location.x;
	}
	
	public int getY(){
		return location.y;
	}
	
	public int getRotation(){
		return rotation;
	}
	
	public String getPic(){
		return pic;
	}
	
	public void update(){
		this.location = new Point(actor.getX(), actor.getY());
		this.rotation = actor.getRotation();
		this.pic = ((ExtendedGreenfootImage) actor.getImage()).getFilename();
	}
	
	public static ActorState fromActor(Actor a){
		return new ActorState(a);
	}
	
}