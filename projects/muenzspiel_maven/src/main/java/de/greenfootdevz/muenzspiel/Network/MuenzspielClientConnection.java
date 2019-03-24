package de.greenfootdevz.muenzspiel.Network;

import de.greenfootdevz.muenzspiel.ExceptionDialog;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class MuenzspielClientConnection extends Thread {
	
	private boolean connected;
	
	private MuenzspielHostData mostRecentData;
	
	private Socket connection;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private InetAddress address;
	private int port;
	
	public MuenzspielClientConnection(InetAddress address, int port, boolean autoconnect) throws IOException {
		this.address = address;
		this.port = port;
		this.mostRecentData = new MuenzspielHostData(new ArrayList<>());
		if (autoconnect) {
			connect();
		}
	}
	
	public void connect() throws IOException {
		checkIsNotConnected();
		this.connection = new Socket(address, port);
		this.connection.setTcpNoDelay(true);
		this.ois = new ObjectInputStream(connection.getInputStream());
		this.oos = new ObjectOutputStream(connection.getOutputStream());
		this.connected = true;
	}
	
	public MuenzspielHostData getRecentHostData() {
		if (mostRecentData != null) {
			return mostRecentData;
		}
		return null;
	}
	
	public void sendUpdate(MuenzspielClientData data) throws IOException {
		oos.writeObject(data);
		oos.flush();
	}
	
	@Override
	public final void run() {
		while (true) {
			try {
				Object o = ois.readObject();
				System.out.println("Read: " + o.toString());
				synchronized (mostRecentData) {
					this.mostRecentData = (MuenzspielHostData) o;
				}
			} catch (Exception e) {
				new ExceptionDialog(e);
			}
		}
	}
	
	private void checkIsNotConnected() throws IOException {
		if (connected) {
			throw new IOException("Already connected!");
		}
	}
	
}