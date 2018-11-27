package Client;

import java.net.InetAddress;

public class ServerConnectionParameter {
    private InetAddress serverIP;
    private int serverPort;

    public InetAddress getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ServerConnectionParameter(InetAddress serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }
}
