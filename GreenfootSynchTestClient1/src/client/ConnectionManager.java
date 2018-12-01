package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import synch.ClientSignInData;
import synch.SignInResponse;

public class ConnectionManager{
	
	private String ip, key;
	
	public ConnectionManager(String ip, String key){
		this.ip = ip;
		this.key = key;
	}
	
	public ServerConnection attemptConnect() throws UnknownHostException, IOException, ClassNotFoundException{
		Socket server = new Socket(ip, 25566);
		ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
		oos.writeObject(new ClientSignInData("rueno", key));
		Object result = ois.readObject();
		if(result instanceof SignInResponse){
			
			switch(((SignInResponse) result).getResponseCode()){
			case SignInResponse.RESULT_ACCEPT:
				System.out.println("accepted!");
				return new ServerConnection(server, ois, oos);
			case SignInResponse.RESULT_WRONG_KEY:
				System.out.println("Wrong key!");
				break;
			case SignInResponse.RESULT_ERROR_UNDEFINDED:
				System.out.println("Unknown reason!");
				break;
			case SignInResponse.RESULT_USERNAME_TAKEN:
				System.out.println("Username already taken!");
				break;
			}
			server.close();
		}
		return null;
	}
	
}