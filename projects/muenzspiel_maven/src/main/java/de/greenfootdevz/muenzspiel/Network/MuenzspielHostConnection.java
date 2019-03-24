package de.greenfootdevz.muenzspiel.Network;

import de.greenfootdevz.muenzspiel.ExceptionDialog;
import de.greenfootdevz.muenzspiel.Actor.Coin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MuenzspielHostConnection extends Thread {

	private MuenzspielClientData mostRecent;

	private InetAddress address;
	private int port;
	private int backlog;

	private ServerSocket server;
	private Socket client;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public MuenzspielHostConnection(InetAddress address, int backlog, int port, boolean autoStart) throws IOException {
		this.address = address;
		this.backlog = backlog;
		this.port = port;
		this.mostRecent = new MuenzspielClientData(new Coin[0]);
		if (autoStart) {
			startServer();
		}
	}

	public void startServer() throws IOException {
		this.server = new ServerSocket(port, backlog, address);
	}

	public void waitForClientConnection() throws IOException {
		this.client = server.accept();
		this.client.setTcpNoDelay(true);
		this.oos = new ObjectOutputStream(client.getOutputStream());
		this.ois = new ObjectInputStream(client.getInputStream());
	}

	public MuenzspielClientData getMostRecent() {
		MuenzspielClientData data = mostRecent;
		return data;
	}

	public void sendUpdate(MuenzspielHostData data) throws IOException {
		oos.writeObject(data);
		oos.flush();
	}

	@Override
	public final void run() {
		while (true) {
			try {
				Object o = ois.readObject();
				synchronized (mostRecent) {
					this.mostRecent = (MuenzspielClientData) o;
				}
			} catch (Exception e) {
				new ExceptionDialog(e);
			}
		}
	}

}