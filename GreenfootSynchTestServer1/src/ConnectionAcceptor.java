
import java.net.ServerSocket;

public class ConnectionAcceptor extends Thread{
	
	private ServerSocket accept;
	private boolean interrupted;
	
	private String key;
	
	public ConnectionAcceptor(ServerSocket accept, String key){
		this.accept = accept;
		this.interrupted = false;
		this.key = key;
	}
	
	@Override
	public void run(){
		try{
			
			while(!interrupted){
				GameClient client = new GameClient(accept.accept());
				ClientAuthenticator clientAuth = new ClientAuthenticator(client, key);
				clientAuth.start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void interrupt(){
		this.interrupted = true;
	}
	
}