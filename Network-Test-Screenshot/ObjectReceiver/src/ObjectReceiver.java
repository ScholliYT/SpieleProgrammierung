import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ObjectReceiver{
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ServerSocket socket = new ServerSocket(25566, 0, InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
		
		Thread clientAcceptor = new Thread(() -> {
			try{
				while(true) {
					Socket client = socket.accept();
					int clientCount = 0;
					Thread clientManager = new Thread(() -> {
						try{
							ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
							while(!client.isClosed()){
								System.out.println(ois.readObject().toString());
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
					});
					clientManager.setName("ClientManager-" + ++clientCount);
					clientManager.start();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		});
		clientAcceptor.start();
	}
}