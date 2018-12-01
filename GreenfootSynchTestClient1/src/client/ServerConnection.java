package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import greenfoot.Actor;

public class ServerConnection{
	
	private static ServerConnection SINGLETONE;
	
	private ArrayList<Actor> mySynchedActors;
	
	private Socket server;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ServerConnection(Socket socket, ObjectInputStream ois, ObjectOutputStream oos){
		this.server = socket;
		this.oos = oos;
		this.ois = ois;
		this.mySynchedActors = new ArrayList<>();
		ServerConnection.SINGLETONE = this;
	}
	
	public Object readObject() throws ClassNotFoundException, IOException{
		return ois.readObject();
	}
	
	public <T extends Serializable> void writeObject(T write) throws IOException{
		oos.reset();
		oos.writeObject(write);
		oos.flush();
	}
	
	public void synchActor(Actor a){
		synchronized(mySynchedActors){
			mySynchedActors.add(a);
		}
	}
	
	public void stopSynchActor(Actor a){
		synchronized(mySynchedActors){
			mySynchedActors.remove(a);
		}
	}
	
	public static ServerConnection getConnection(){
		if(ServerConnection.SINGLETONE == null) throw new NullPointerException("ServerConnection is null!");
		return ServerConnection.SINGLETONE;
	}
	
}