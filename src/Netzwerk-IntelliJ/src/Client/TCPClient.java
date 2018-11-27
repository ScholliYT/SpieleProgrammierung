package Client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

    private final InetAddress serverIP;
    private final int serverPort;

    private Socket serverSocket;

    private OutputStreamWriter osw;
    private InputStreamReader isr;
    private boolean isConnected = false;

    public TCPClient(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }


    public void connect() {
        try
        {
            serverSocket = new Socket(serverIP, serverPort);

            // obtaining input and out streams
            osw = new OutputStreamWriter(serverSocket.getOutputStream());
            isr = new InputStreamReader(serverSocket.getInputStream());

            isConnected = true;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void write(String msg) throws IOException {
        if(!isConnected) {
            throw new NotConnectedException();
        }
        try {
            osw.write(msg);
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            osw.close();
            isr.close();
        }
    }




}
