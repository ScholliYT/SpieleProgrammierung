import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ServerConnection{
	
	private ServerSocket socket;
	
	public ServerConnection(ProjectFrame frame, InetAddress ip, int port) throws UnknownHostException, IOException{
		this.socket = new ServerSocket(port, 0, ip);
		Thread connector = new Thread(() -> {
			while(true){
				try {
					Socket client = socket.accept();
					if(frame.getAllowConnections()){
						int result = JOptionPane.showConfirmDialog(frame, "<html>Der Client mit IP " + socket.getInetAddress().toString() + "Versucht sich zu verbinden.<br>Verbindung zulassen?</html>", "Verbindung aufbauen?", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if(result == JOptionPane.YES_OPTION){
							ClientHandler handler = new ClientHandler(client, frame);
						}
					}else{
						client.close();
					}
				} catch (Exception e){
					e.printStackTrace();
				}
				
			}
		});
		connector.start();
	}
	
}