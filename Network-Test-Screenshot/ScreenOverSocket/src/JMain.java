import java.net.InetAddress;

import javax.swing.UIManager;

public class JMain{

	private static final int PORT = 25560;

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			InetAddress ip = InetAddress.getLocalHost();
			
			ProjectFrame frame = new ProjectFrame();
			frame.setVisible(true);
			new ServerConnection(frame, ip, PORT);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}