package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class GameClient{
	
	private String username;
	
	private Socket client;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private final Object lock = new Object();
	
	public GameClient(Socket client){
		this.client = client;
		this.username = "unknown";
		try{
			this.in = new ObjectInputStream(client.getInputStream());
			this.out = new ObjectOutputStream(client.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public ObjectInputStream getInputStream(){
		return in;
	}
	
	public Object readNextObject() throws ClassNotFoundException, IOException{
		return in.readObject();
	}
	
	public ObjectOutputStream getOutputStream(){
		return out;
	}
	
	public <T extends Serializable> void sendObject(T obj) throws IOException{
		out.reset();
		out.writeObject(obj);
		out.flush();
	}
	
}