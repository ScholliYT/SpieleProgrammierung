package server;
import java.io.ObjectInputStream;

import synch.ClientSignInData;
import synch.SignInResponse;

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
						client.sendObject(new SignInResponse(SignInResponse.RESULT_ACCEPT, GameServer.getInstance().getNextClientId()));
						GameServer.getInstance().addNewClient(client);
					}else{
						client.sendObject(new SignInResponse(SignInResponse.RESULT_WRONG_KEY, -1));
					}
				}
				client.sendObject(new SignInResponse(SignInResponse.RESULT_ERROR_UNDEFINDED, -1));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}