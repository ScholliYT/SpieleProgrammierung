
import java.io.ObjectInputStream;

public class ClientAuthenticator extends Thread{
	
	private GameClient client;
	private String key;
	
	public ClientAuthenticator(GameClient client, String key){
		this.client = client;
		this.key = key;
	}
	
	@Override
	public void run(){
		ObjectInputStream ois = client.getInputStream();
		synchronized(ois){
			try{
				Object result = client.readNextObject();
				if(result instanceof ClientSignInData){
					ClientSignInData data = (ClientSignInData) result;
					if(data.acceptKey(key)){
						GameServer server = GameServer.getInstance();
						client.sendObject(new SignInResponse(SignInResponse.RESULT_ACCEPT, server.getNextClientId(), server.getNextIdRangeStart()+1, server.getNextIdRangeStart()+100));
						GameServer.getInstance().addNewClient(client);
					}else{
						client.sendObject(new SignInResponse(SignInResponse.RESULT_WRONG_KEY, -1, 0, 0));
					}
				}
				client.sendObject(new SignInResponse(SignInResponse.RESULT_ERROR_UNDEFINDED, -1, 0, 0));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}