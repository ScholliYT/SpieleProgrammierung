import greenfoot.*;
import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;
import java.util.Random;

public class PongWorld extends World{

    // Networking
    private final String IP = "192.168.178.41"; // Default IP
    private final int PORT = 25566;

    private ServerSocket host;
    private Socket client;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean isHost;

    // Actors
    private Ball ball;
    private Bat bat;
    private RemoteBat remote;

    private int dx, dy;

    private final int BALL_WALL_OFFSET = 20;
    private final int BALL_MAX_SPEED_Y = 2;

    public PongWorld() throws Exception{
        super(1000, 500, 1);

        int result = JOptionPane.showConfirmDialog(null, "Möchten Sie diesen Rechner als Host verwenden?", "Hostwahl", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.CLOSED_OPTION) return;
        isHost = (result == JOptionPane.YES_OPTION);

        this.bat = new Bat();
        this.remote = new RemoteBat();
        this.ball = new Ball();
        addObject(ball, getWidth()/2, getHeight()/2);
        resetBall();

        if(isHost){
            System.out.println("Starting Host on this machine...");

            addObject(bat, BALL_WALL_OFFSET, getHeight()/2);
            addObject(remote, getWidth()-BALL_WALL_OFFSET, getHeight()/2);


            this.host = new ServerSocket(PORT, 50, InetAddress.getByName(IP));
            System.out.println("Waiting for a client to connect...");
            this.client = host.accept();
            client.setTcpNoDelay(true);
            System.out.println("Client from " + client.getInetAddress().getHostAddress() + " connected.");
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
        }else{
            System.out.println("Starting Client on this machine...");

            addObject(bat, getWidth()-BALL_WALL_OFFSET, getHeight()/2);
            addObject(remote, BALL_WALL_OFFSET, getHeight()/2);


            client = new Socket(IP, this.PORT);
            client.setTcpNoDelay(true);

            this.oos = new ObjectOutputStream(client.getOutputStream());
            this.ois = new ObjectInputStream(client.getInputStream());
        }
        System.out.println("Starting the Game!");
        Greenfoot.start();
    }

    public void act(){
        try{
            if(isHost){
                handleHostTasks();
            }else{
                handleClientTasks();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void handleHostTasks() throws Exception{
        if(ball.getX() < ball.getImage().getWidth()/2){ //Punkt für client
            resetBall();
            //TODO puntke geben (async?)
        }else if(ball.getX() > getWidth() - ball.getImage().getWidth()/2){ //Punkt für host
            resetBall();
            //TODO puntke geben (async?)
        }else if(ball.getIntesecting(Bat.class) != null){ //Host trifft ball
           handleBatHit(bat);
        }else if(ball.getIntesecting(RemoteBat.class) != null){ //Client trifft
            handleBatHit(remote);
        }else{ //Ball fliegt einfach weiter (Bounce oben / unten)
            ball.setLocation(ball.getX() + dx, ball.getY() + dy);
            if(ball.getY() <= ball.getImage().getHeight()/2 || ball.getY() >= getHeight() - ball.getImage().getHeight()/2){
                dy = -dy;
            }
        }
        PongClientData data = (PongClientData) ois.readObject(); // Daten vom client empfangen
        oos.writeObject(new PongHostData(bat.getX(), bat.getY(), ball.getX(), ball.getY())); // aktuelle Daten an Client senden
        oos.flush();
        remote.setLocation(data.getX(), data.getY()); // Empfangene Daten vom Client anzeigen
    }

    private void handleBatHit(Actor bat){
        dx = -dx;
        dy = (ball.getY() - bat.getY()) / 2;
        if(dy > BALL_MAX_SPEED_Y) dy = BALL_MAX_SPEED_Y;
        if(dy < -BALL_MAX_SPEED_Y) dy = -BALL_MAX_SPEED_Y;
        ball.setLocation(ball.getX() + dx, ball.getY() + dy);
    }

    private void resetBall(){
       ball.setLocation(getWidth()/2, getHeight()/2);
       dx = new Random().nextBoolean() ? 3 : -3; // randomly choose starting direction on x-Axis
       dy = 0;
    }

    private void handleClientTasks() throws Exception{
        oos.writeObject(new PongClientData(bat.getX(), bat.getY()));
        oos.flush();
        PongHostData data = (PongHostData) ois.readObject();
        this.ball.setLocation(data.getBallPos().x, data.getBallPos().y);
        this.remote.setLocation(data.getBatPos().x, data.getBatPos().y);
    }
}