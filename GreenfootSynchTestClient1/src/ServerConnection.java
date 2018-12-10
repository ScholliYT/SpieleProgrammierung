
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import greenfoot.Actor;

public class ServerConnection extends Thread{
	
	private static ServerConnection SINGLETONE;
	
	private HashMap<Integer, Actor> actorToId;
	
	private ArrayList<ActorState> mySynchedActors;
	
	private Socket server;
	
	private int idRangeStart, idRangeEnd, currentId;
	
	private SynchronizedTickData mostRecentData;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public ServerConnection(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, int idRangeStart, int idRangeEnd){
		this.server = socket;
		this.oos = oos;
		this.ois = ois;
		this.idRangeStart = idRangeStart;
		this.currentId = idRangeStart;
		this.idRangeEnd = idRangeEnd;
		this.actorToId = new HashMap<>();
		this.mySynchedActors = new ArrayList<>();
		setName("ServerConnectionManager");
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
	
	public void addStrangerActor(ActorState state){
		actorToId.put(state.getId(), state.toActor());
	}
	
	public void synchActor(Actor a){
		synchronized(mySynchedActors){
			mySynchedActors.add(ActorState.fromActor(a, currentId));
		}
		currentId++; //TODO Outofrange abfangen
	}
	
	public void stopSynchActor(Actor a){
		synchronized(mySynchedActors){
			for(ActorState state: mySynchedActors){
				if(state.getActor().equals(a)) {
					mySynchedActors.remove(state);
				}
			}
		}
	}
	
	public SynchronizedTickData getMostRecentData(){
		return mostRecentData;
	}
	
	@Override
	public void run(){
		while(true){
			try{
				updateActors();
				writeObject(new ClientSynchData(mySynchedActors.toArray(new ActorState[mySynchedActors.size()])));
				Object info = readObject();
				if(info instanceof SynchronizedTickData){
					this.mostRecentData = (SynchronizedTickData) info;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void updateActors(){
		for(ActorState state: mySynchedActors) {
			state.update();
		}
	}
	
	public static ServerConnection getConnection(){
		if(ServerConnection.SINGLETONE == null) throw new NullPointerException("ServerConnection is null!");
		return ServerConnection.SINGLETONE;
	}
	
}