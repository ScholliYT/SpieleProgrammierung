

import java.awt.Point;
import java.io.Serializable;

import greenfoot.Actor;

public class ActorState implements Serializable{
	
	private static final long serialVersionUID = 8760631322861178939L;
	
	private transient Actor actor;
	
	private Point location;
	private int rotation, id;
	private String pic;
	
	private ActorState(Actor actor, int id){
		this.actor = actor;
		this.id = id;
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
	
	public int getId(){
		return id;
	}
	
	public void update(){
		this.location = new Point(actor.getX(), actor.getY());
		this.rotation = actor.getRotation();
		this.pic = ((ExtendedGreenfootImage) actor.getImage()).getFilename();
	}
	
	public Actor toActor(){
		Actor a = new Actor(){
		};
		a.setImage(pic);
		a.setLocation(getX(), getY());
		a.setRotation(rotation);
		return a;
	}
	
	//Clientseitige Methode
	public Actor getActor(){
		return actor;
	}
	
	public static ActorState fromActor(Actor a, int id){
		return new ActorState(a, id);
	}
	
}