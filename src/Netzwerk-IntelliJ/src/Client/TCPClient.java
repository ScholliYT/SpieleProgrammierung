package Client;

import Util.Command;
import Util.CommandConverter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

    private final InetAddress serverIP;
    private final int serverPort;

    private Socket serverSocket;


    private boolean isConnected = false;
    private DataOutputStream dos;
    private DataInputStream dis;

    public TCPClient(ServerConnectionParameter server) {
        this.serverIP = server.getServerIP();
        this.serverPort = server.getServerPort();
    }


    public void connect() {
        try {
            serverSocket = new Socket(serverIP, serverPort);

            // obtaining input and out streams

            this.dis = new DataInputStream(serverSocket.getInputStream());
            this.dos = new DataOutputStream(serverSocket.getOutputStream());
            isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Command cmd) throws IOException {
        String commandText = CommandConverter.destruct(cmd);
        write(commandText);
    }

    public void write(String msg) throws IOException {
        if (!isConnected) {
            throw new NotConnectedException();
        }
        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(dos));
        outputWriter.write(msg);
        outputWriter.flush();
    }

    public void close() {
        try {
            dos.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
