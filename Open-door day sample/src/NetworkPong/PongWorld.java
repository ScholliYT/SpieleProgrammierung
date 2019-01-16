
import greenfoot.*;
import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class PongWorld extends World{
    
    private ServerSocket host;
    private Socket client;
    
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    private boolean isHost;
    
    private Ball ball;
    private Bat bat;
    private RemoteBat remote;
    
    private int dx, dy;
    
    public PongWorld() throws Exception{
        super(1000, 500, 1);
        isHost = true;
        this.dx = 3;
        this.dy = 0;
        
        int result = JOptionPane.showConfirmDialog(null, "Möchten Sie diesen Rechner als Host verwenden?", "Hostwahl", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.CLOSED_OPTION) return;
        isHost = (result == JOptionPane.YES_OPTION);
        
        if(isHost){
            this.bat = new Bat();
            this.remote = new RemoteBat();
            this.ball = new Ball();
            
            addObject(bat, 20, 250);
            addObject(remote, 980, 250);
            addObject(ball, 500, 250);
            
            this.host = new ServerSocket(25566, 50, InetAddress.getByName("192.168.178.73"));
            this.client = host.accept();
            client.setTcpNoDelay(true);
            
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
            Greenfoot.start();
        }else{
            this.bat = new Bat();
            this.remote = new RemoteBat();
            this.ball = new Ball();
            
            addObject(bat, 980, 250);
            addObject(remote, 20, 250);
            addObject(ball, 500, 250);
            
            client = new Socket("192.168.178.73", 25566);
            client.setTcpNoDelay(true);
            
            this.oos = new ObjectOutputStream(client.getOutputStream());
            this.ois = new ObjectInputStream(client.getInputStream());
            
            Greenfoot.start();
        }
    }
    
    public void act(){
        try{
            if(isHost){
                handleHostTasks();
            }else{
                handleClientTasks();
            }
        }catch(Exception e){}
    }
    
    private void handleHostTasks() throws Exception{
        if(ball.getX() < 5){ //Punkt für client
            //TODO puntke geben
            resetBall();
        }else if(ball.getX() > 995){ //Punkt für host
            //TODO puntke geben
            resetBall();
        }else if(ball.getIntesecting(Bat.class) != null){ //Host trifft ball
           handleBatHit(bat);
        }else if(ball.getIntesecting(RemoteBat.class) != null){ //Client trifft
            handleBatHit(remote);
        }else{ //Ball fliegt einfach weiter
            ball.setLocation(ball.getX() + dx, ball.getY() + dy);
            if(ball.getY() <= 5 || ball.getY() >= 495){
                dy = -dy;
            }
        }
        PongClientData data = (PongClientData) ois.readObject();
        oos.writeObject(new PongHostData(bat.getX(), bat.getY(), ball.getX(), ball.getY()));
        oos.flush();
        remote.setLocation(data.getX(), data.getY());
    }
    
    private void handleBatHit(Actor bat){
        dx = -dx;
        dy = (ball.getY() - bat.getY()) / 2;
        if(dy > 2) dy = 2;
        if(dy < -2) dy = -2;
        ball.setLocation(ball.getX() + dx, ball.getY() + dy);
    }
    
    private void resetBall(){
       ball.setLocation(500, 250);
       dx = 3;
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