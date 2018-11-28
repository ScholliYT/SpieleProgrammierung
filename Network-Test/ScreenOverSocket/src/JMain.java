import java.net.InetAddress;

import javax.swing.UIManager;

public class JMain{
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			
			ProjectFrame frame = new ProjectFrame();
			frame.setVisible(true);
			new ServerConnection(frame);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}