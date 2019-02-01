package de.greenfootdevz.charactereditor.Network;

import de.greenfootdevz.charactereditor.ExceptionDialog;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class PongClientConnection extends Thread{
	
	private boolean connected;
	
	private PongHostData mostRecentData;
	
	private Socket connection;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private InetAddress address;
	private int port;
	
	public PongClientConnection(InetAddress address, int port,
			boolean autoconnect) throws IOException{
		this.address = address;
		this.port = port;
		this.mostRecentData = new PongHostData(0, 0, 0, 0, -1, -1, 0, 0);
		if(autoconnect){
			connect();
		}
	}
	
	public void connect() throws IOException{
		checkIsNotConnected();
		this.connection = new Socket(address, port);
		this.connection.setTcpNoDelay(true);
		this.ois = new ObjectInputStream(connection.getInputStream());
		this.oos = new ObjectOutputStream(connection.getOutputStream());
		this.connected = true;
	}
	
	public PongHostData getRecentHostData(){
		if(mostRecentData != null){
			return mostRecentData;
		}
		return null;
	}
	
	public void sendUpdate(PongClientData data) throws IOException{
		oos.writeObject(data);
		oos.flush();
	}
	
	@Override
	public final void run(){
		while(true){
			try{
				Object o = ois.readObject();
				synchronized (mostRecentData) {
					this.mostRecentData = (PongHostData) o;
				}
			}catch(Exception e){
				new ExceptionDialog(e).setVisible(true);
			}
		}
	}
	
	private void checkIsNotConnected() throws IOException{
		if(connected){
			throw new IOException("Already conncted!");
		}
	}
	
}