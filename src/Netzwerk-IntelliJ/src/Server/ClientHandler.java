package Server;

import Util.Command;
import Util.CommandConverter;
import Util.CommandNotFoundException;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public String getHostAddress() {
        return clientSocket.getInetAddress().getHostAddress();
    }

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
                if(received==null) continue;

                System.out.println(getHostAddress() + "< " + received);

                if(CommandConverter.isValidCommand(received)) {
                    Command command = CommandConverter.construct(received);
                    System.out.println("    Received command from " + getHostAddress() + ": " + command.name());

                    if(command == Command.LOGOUT) {
                        this.clientSocket.close();
                        break;
                    }
                }

                received = received.toUpperCase();
                outputWriter.write(received); // Echo with uppercase message
            } catch (IOException | CommandNotFoundException e) {
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
