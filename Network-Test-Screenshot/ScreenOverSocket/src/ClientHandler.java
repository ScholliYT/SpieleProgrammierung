import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
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
		frame.setTitle("Bildschrimübertragung von: " + client.getInetAddress().toString());
		while(true){
			try{
//				System.out.println("Receiving!");
				byte[] data = (byte[]) ois.readObject();
				frame.update(new ImageIcon(ImageIO.read(new ByteArrayInputStream(data))));
			}catch(Exception e){
				e.printStackTrace();
				break;
			}
		}
	}
	
}