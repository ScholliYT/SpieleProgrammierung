package server;
import java.net.ServerSocket;
import java.util.ArrayList;

public class GameServer implements Runnable{
	
	private static GameServer SINGLETONE;
	
	private ServerSocket server;
	
	private ArrayList<GameClient> clients;
	private volatile int nextId;
	
	public GameServer(ServerSocket server){
		this.server = server;
		this.clients = new ArrayList<>();
		this.nextId = 0;
		
		GameServer.SINGLETONE = this;
	}
	
	@Override
	public void run(){
		//TODO Spiele starten, beenden, main-welt zeugs, basically hautpmethode des Servers, die alles steuert
		return;
	}
	
	public int getNextClientId(){
		return  nextId++;
	}
	
	public void addNewClient(GameClient client){
		synchronized(clients){
			clients.add(client);
		}
	}
	
	public static GameServer getInstance(){
		if(GameServer.SINGLETONE == null) throw new NullPointerException("Gameserver is null!");
		return GameServer.SINGLETONE;
	}
	
}