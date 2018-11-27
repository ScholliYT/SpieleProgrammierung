package Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int port;

    private ServerSocket serverSocket;

    public TCPServer(int port) {
        this.port = port;
    }

    public void start() {
        try {
            System.out.println("Starting server at: " + InetAddress.getLocalHost().getHostAddress() + ":" + port);


            serverSocket = new ServerSocket(port);

            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void run() throws IOException {
        while (true)
        {
            // Accept the incoming request
            Socket clientSocket = serverSocket.accept();

            System.out.println("New client request received from: " + clientSocket.getInetAddress().getHostAddress());


            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler clientHandler = new ClientHandler(clientSocket);

            // Create a new Thread with this object.
            Thread t = new Thread(clientHandler);


            // start the thread for the client.
            t.start();
        }
    }

}
