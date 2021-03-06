
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;

public class GameServer implements Runnable{
	
	private static GameServer SINGLETONE;
	
	private ServerSocket server;
	
	private ArrayList<GameClient> clients;
	
	private volatile int nextId;
	private volatile int nextIdRangeStart;
	
	public GameServer(ServerSocket server) {
		this.server = server;
		this.clients = new ArrayList<>();
		this.nextId = 0;
		this.nextIdRangeStart = 0;
		
		GameServer.SINGLETONE = this;
	}
	
	public static int sizeof(Object obj) throws IOException {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
		
		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();
		
		return byteOutputStream.toByteArray().length;
	}
	
	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			if(clients.size() <= 1) return;
			SynchronizedTickData tickData = new SynchronizedTickData();
			for (GameClient client : clients) {
				ClientSynchData data = client.getMostRecentClientData();
				if (data != null) {
					tickData.addClient(data.getMyActors());
				}
			}
			
			for (GameClient client : clients) {
				try {
					client.sendObject(tickData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Tick took " + (System.currentTimeMillis() - start) + "ms.");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getNextClientId() {
		this.nextIdRangeStart += 100;
		return nextId++;
	}
	
	public int getNextIdRangeStart() {
		return nextIdRangeStart;
	}
	
	public void addNewClient(GameClient client) {
		synchronized (clients) {
			clients.add(client);
		}
		Thread clientThread = new Thread(client);
		clientThread.setDaemon(true);
		clientThread.setName("ClientThread-" + client.getUsername());
		clientThread.start();
	}
	
	public static GameServer getInstance() {
		if (GameServer.SINGLETONE == null)
			throw new NullPointerException("Gameserver is null!");
		return GameServer.SINGLETONE;
	}
	
}