import Client.TCPClient;
import Server.TCPServer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify whether you want to start a server (arg: server) or client (arg: client)");
        }
        switch (args[0].toLowerCase()) {
            case "server":
                startServer();
                break;
            case "client":
                startClient();
                break;
            default:
                System.out.println("Unknown argument at pos 0: " + args[0]);
                break;

        }
    }

    private static void startClient() {

        Scanner inputScanner = new Scanner(System.in);

        String serverIPandPort;
        do {
            System.out.println("ip:port (192.168.1.2:25566) of server?");
            serverIPandPort = inputScanner.nextLine();
        } while (!serverIPandPort.contains(":") || serverIPandPort.split(":").length != 2);

        InetAddress serverIP;
        try {
            serverIP = InetAddress.getByName(serverIPandPort.split(":")[0]);
            int serverPort = Integer.parseInt(serverIPandPort.split(":")[1]);
            TCPClient client = new TCPClient(serverIP, serverPort);

                client.connect();
                client.write("Hello");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void startServer() {
        TCPServer server = new TCPServer(25566);
        server.start();
    }
}
