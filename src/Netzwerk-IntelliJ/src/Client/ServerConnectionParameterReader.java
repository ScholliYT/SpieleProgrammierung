package Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerConnectionParameterReader {
    private Scanner inputScanner;


    public ServerConnectionParameterReader(Scanner inputScanner) {
        this.inputScanner = inputScanner;
    }

    public ServerConnectionParameter invoke() {
        String serverIPandPort;
        InetAddress serverIP;
        int serverPort;
        do {
            System.out.println("ip:port (192.168.1.2:25566) of server?");
            serverIPandPort = inputScanner.nextLine();
            try {
                serverIP = InetAddress.getByName(serverIPandPort.split(":")[0]);
                serverPort = Integer.parseInt(serverIPandPort.split(":")[1]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                serverIP = null;
                serverPort = -1;
            }
        } while (!serverIPandPort.contains(":") || serverIPandPort.split(":").length != 2 || serverIP == null || serverPort == -1);
        return new ServerConnectionParameter(serverIP, serverPort);
    }
}
