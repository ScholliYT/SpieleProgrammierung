package Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private DataInputStream dis;
    private DataOutputStream dos;

    // constructor
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {

        // obtain input and output streams
        try {
            this.dis = new DataInputStream(clientSocket.getInputStream());
            this.dos = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(dis));
        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(dos));
        while (true) {
            try {
                // receive the string
                String received = inputReader.readLine();

                System.out.println(received);

                if (received.equals("logout")) {
                    this.clientSocket.close();
                    break;
                }

                received = received.toUpperCase();
                outputWriter.write(received);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
