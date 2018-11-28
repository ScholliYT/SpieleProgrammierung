import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

public class ClientHandler extends Thread{
	
	private Socket client;
	private ProjectFrame frame;
	
	private ObjectInputStream ois;
	
	public ClientHandler(Socket client, ProjectFrame frame) throws IOException{
		this.client = client;
		client.setTcpNoDelay(true);
		this.frame = frame;
		this.ois = new ObjectInputStream(client.getInputStream());
		this.start();
	}
	
	@Override
	public void run() {
		while(true){
			try{
//				System.out.println("Receiving!");
				Object in = ois.readObject();
				if(in instanceof ImageIcon){
					frame.update((ImageIcon) in);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}