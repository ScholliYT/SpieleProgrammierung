import Client.ServerConnectionParameter;
import Client.ServerConnectionParameterReader;
import Client.TCPClient;
import Server.TCPServer;
import Util.Command;

import java.io.IOException;
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

        ServerConnectionParameter serverConnection = new ServerConnectionParameterReader(inputScanner).invoke();

        try {
            TCPClient client = new TCPClient(serverConnection);

            client.connect();
            client.write("hello server");
            client.write(Command.LOGOUT);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void startServer() {
        TCPServer server = new TCPServer(25566);
        server.start();
    }

}
