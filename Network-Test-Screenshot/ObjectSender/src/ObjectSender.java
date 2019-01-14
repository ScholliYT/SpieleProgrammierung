import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ObjectSender{
	
	public static void main(String[] args) {
		try{
			Socket client = new Socket(InetAddress.getByName("192.168.178.73"), 25566);
			
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			
			while(!client.isClosed()){
				long nau = System.currentTimeMillis();
				oos.writeObject(new TestObjekt());
				System.out.println("Time: " + (System.currentTimeMillis() - nau) + "ms.");
			}
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}