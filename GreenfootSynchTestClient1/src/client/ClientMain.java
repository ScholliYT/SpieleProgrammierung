package client;

public class ClientMain{
	
	public static void main(String[] args){
		try{
			ServerLoginFrame frame = new ServerLoginFrame();
			frame.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}