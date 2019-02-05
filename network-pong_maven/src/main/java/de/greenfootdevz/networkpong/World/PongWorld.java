package de.greenfootdevz.networkpong.World;

import de.greenfootdevz.networkpong.*;
import de.greenfootdevz.networkpong.Actor.Ball;
import de.greenfootdevz.networkpong.Actor.Bat;
import de.greenfootdevz.networkpong.Actor.Booster;
import de.greenfootdevz.networkpong.Actor.RemoteBat;
import de.greenfootdevz.networkpong.Network.PongClientConnection;
import de.greenfootdevz.networkpong.Network.PongClientData;
import de.greenfootdevz.networkpong.Network.PongHostConnection;
import de.greenfootdevz.networkpong.Network.PongHostData;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

import java.net.InetAddress;
import java.util.Random;

public class PongWorld extends World {

//    private ServerSocket host;
//    private Socket client;

//    private ObjectInputStream ois;
//    private ObjectOutputStream oos;

    private boolean isHost;

    // Actors
    private Booster booster;
    private Ball ball;
    private Bat bat;
    private RemoteBat remote;

    private Random r;

    private double dx, dy;
    private int pointsHost, pointsClient;

    private PongClientConnection clientConnection;
    private PongHostConnection hostConnection;

    private final int BAT_WALL_OFFSET = 20;
    private final int BALL_SPEED = 8;
    private final double BALL_MAX_BOUNCE_ANGLE = 5*Math.PI / 12;

    public PongWorld() throws Exception {
        super(800, 600, 1);

        //exception(null); //Throws exception in order to test dialog

        GameInitFrame frame = new GameInitFrame();
        frame.setVisible(true);
        Thread.sleep(100);
        this.isHost = frame.isHost();
        InetAddress ip = frame.getSelectedAddress();
        int port = frame.getSelectedPort();

        this.r = new Random();

        this.bat = new Bat();
        this.remote = new RemoteBat();
        this.ball = new Ball();
        addObject(ball, getWidth() / 2, getHeight() / 2);
        resetBall();

        if (isHost) {
            System.out.println("Starting Host on this machine...");

            addObject(bat, BAT_WALL_OFFSET, getHeight() / 2);
            addObject(remote, getWidth() - BAT_WALL_OFFSET, getHeight() / 2);


            this.hostConnection = new PongHostConnection(ip, 50, port, true);
            System.out.println("Waiting for a client to connect...");
            hostConnection.waitForClientConnection();
            hostConnection.start();
//            System.out.println("Client from " + client.getInetAddress().getHostAddress() + " connected.");
//            ois = new ObjectInputStream(client.getInputStream());
//            oos = new ObjectOutputStream(client.getOutputStream());
        } else {
            System.out.println("Starting Client on this machine...");

            addObject(bat, getWidth() - BAT_WALL_OFFSET, getHeight() / 2);
            addObject(remote, BAT_WALL_OFFSET, getHeight() / 2);


//            client = new Socket(ip, port);
//            client.setTcpNoDelay(true);
//
//            this.oos = new ObjectOutputStream(client.getOutputStream());
//            this.ois = new ObjectInputStream(client.getInputStream());

            this.clientConnection = new PongClientConnection(ip, port, true);
            clientConnection.start();
        }


        System.out.println("Starting the Game!");
        Greenfoot.start();
    }

    public void act() {
        try {
            if (isHost) {
                handleHostTasks();
            } else {
                handleClientTasks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleHostTasks() throws Exception {
        double newBallX = ball.getActualX() + dx;
        double newBallY = ball.getActualY() + dy;
        int ballRadius = ball.getImage().getWidth() / 2; // assuming ball is a circle

        String currentSoundFile = "";

        // Top and bottom edges -> bounce
        if (newBallY - ballRadius < 0) {
            newBallY = -newBallY;
            dy = -dy;
            currentSoundFile = "pong_8bit_hitwall.wav";
        } else if (newBallY + ballRadius > getHeight() - 1) {
            // newBallY -= 2 * ((newBallY + ballRadius) - (getHeight() - 1));
            newBallY = getHeight() - 1 + newBallY;
            dy = -dy;
            currentSoundFile = "pong_8bit_hitwall.wav";
        }


        //When I wrote this, only God and I understood what I was doing
        //Now, God only knows

        //                   .
        //         /^\     .
        //    /\   "V"
        //   /__\   I      O  o         ###################################
        //  //..\\  I     .             ##  THIS IS MAGIC | DON'T TOUCH  ##
        //  \].`[/  I                   ###################################
        //  /l\/j\  (]    .  O
        // /. ~~ ,\/I          .
        // \\L__j^\/I       o
        //  \/--v}  I     o   .
        //  |    |  I   _________
        //  |    |  I c(`       ')o
        //   |    l  I   \.     ,/
        // _/j  L l\_!  _//^---^\\_

        // Host (left)
        if (newBallX < BAT_WALL_OFFSET + bat.getImage().getWidth() / 2 && ball.getActualX() >= BAT_WALL_OFFSET + bat.getImage().getWidth() / 2) {
            double intersectX = BAT_WALL_OFFSET + bat.getImage().getWidth() / 2.0; // this is constant
            double intersectY = ball.getActualY() - ((ball.getActualX() - (BAT_WALL_OFFSET + bat.getImage().getWidth() / 2.0)) * (ball.getActualY() - newBallY)) / (ball.getActualX() - newBallX); // TODO: wtf?

            // check for hit
            if (intersectY >= bat.getY() - bat.getImage().getHeight() / 2 && intersectY <= bat.getY() + bat.getImage().getHeight() / 2) {
                double relativeIntersectY = bat.getY() - intersectY;
                double bounceAngle = (relativeIntersectY / bat.getImage().getHeight() / 2) * (BALL_MAX_BOUNCE_ANGLE);

                double ballSpeed = Math.sqrt(dx * dx + dy * dy); // pythagoras
                // double ballTravelLeft = (newBallY - intersectY) / (newBallY - ball.getActualY());

                dx = BALL_SPEED * Math.cos(bounceAngle);
                dy = BALL_SPEED * -Math.sin(bounceAngle);

                // newBallX = intersectX + ballTravelLeft * ballSpeed * Math.cos(bounceAngle);
                // newBallY = intersectY + ballTravelLeft * ballSpeed * Math.sin(bounceAngle);

                newBallX = intersectX + ballSpeed * Math.cos(bounceAngle);
                newBallY = intersectY + ballSpeed * Math.sin(bounceAngle);

                currentSoundFile = "pong_8bit_hitpaddle.wav";
            }
        }

        // Remote (right)
        if (newBallX > getWidth() - BAT_WALL_OFFSET - remote.getImage().getWidth() / 2 && ball.getActualX() <= getWidth() - BAT_WALL_OFFSET - bat.getImage().getWidth() / 2) {
            double intersectX = getWidth() - BAT_WALL_OFFSET - remote.getImage().getWidth() / 2.0; // this is constant
            double intersectY = ball.getActualY() - ((ball.getActualX() - (getWidth() - BAT_WALL_OFFSET - remote.getImage().getWidth() / 2)) * (ball.getActualY() - newBallY)) / (ball.getActualX() - newBallX); // TODO: wtf?

            // check for hit
            if (intersectY >= remote.getY() - remote.getImage().getHeight() / 2 && intersectY <= remote.getY() + remote.getImage().getHeight() / 2) {
                double relativeIntersectY = remote.getY() - intersectY;
                double bounceAngle = (relativeIntersectY / remote.getImage().getHeight() / 2) * (BALL_MAX_BOUNCE_ANGLE);

                double ballSpeed = Math.sqrt(dx * dx + dy * dy); // pythagoras
//                double ballTravelLeft = (newBallY - intersectY) / (newBallY - ball.getActualY());

                dx = BALL_SPEED * Math.cos(bounceAngle) * -1;
                dy = BALL_SPEED * Math.sin(bounceAngle) * -1;

//                newBallX = intersectX - ballTravelLeft * ballSpeed * Math.cos(bounceAngle);
//                newBallY = intersectY - ballTravelLeft * ballSpeed * Math.sin(bounceAngle);

                newBallX = intersectX - ballSpeed * Math.cos(bounceAngle);
                newBallY = intersectY - ballSpeed * Math.sin(bounceAngle);

                currentSoundFile = "pong_8bit_hitpaddle.wav";
            }
        }

        // Left and right edges, add to the score and reset the ball
        if (newBallX - ballRadius < 0) {
            resetBall();
            pointsClient++;
            currentSoundFile = "pong_8bit_scorepoint.wav";
        } else if (newBallX + ballRadius > getWidth() - 1) {
            resetBall();
            pointsHost++;
            currentSoundFile = "pong_8bit_scorepoint.wav";
        } else {
            // Finally, copy new position to ball
            ball.setLocation(newBallX, newBallY);
        }


//        if (ball.getX() < ball.getImage().getWidth() / 2) {
//        //if (newBallX - ball.getImage().getWidth() / 2 < bat.getImage().getWidth() / 2 + BAT_WALL_OFFSET) { //Punkt für client
//            resetBall();
//            pointsClient++;
//            currentSoundFile = "pong_8bit_scorepoint.wav";
//
//        } else if (ball.getX() > getWidth() - ball.getImage().getWidth() / 2) { //Punkt für host
//            resetBall();
//            pointsHost++;
//            currentSoundFile = "pong_8bit_scorepoint.wav";
//        }




        // ############################################################################################################
        // # Post processing                                                                                          #
        // ############################################################################################################

        // TODO: activate sound
        currentSoundFile = "";
        if (!"".equals(currentSoundFile)) {
            Greenfoot.playSound(currentSoundFile);
        }

        if (booster == null && r.nextInt(1000) == 0) { //Einen Booster auf der Karte spawnen mit Chance 0.1%
            int boosterX = randomNbrInRange(2 * BAT_WALL_OFFSET, getWidth() - 2 * BAT_WALL_OFFSET);
            int boosterY = randomNbrInRange(0, getHeight());

            booster = new Booster();
            addObject(booster, boosterX, boosterY);
        }
        showScoreboard(pointsHost, pointsClient);
        PongClientData data = hostConnection.getMostRecent(); // Daten vom client empfangen
        if (data == null) return;
        hostConnection.sendUpdate(new PongHostData(bat.getX(), bat.getY(), ball.getX(), ball.getY(), (booster != null ? booster.getX() : -1), (booster != null ? booster.getY() : -1),
                pointsHost, pointsClient, currentSoundFile)); // aktuelle Daten an Client senden
        remote.setLocation(data.getX(), data.getY()); // Empfangene Daten vom Client anzeigen
    }

    /*
     * Generates a random int in range [min|max] (bounds inclusive).
     * For this to work: min <= max.
     */
    private int randomNbrInRange(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max is smaller than min.");
        }
        return r.nextInt((max - min) + 1) + min;
    }

    private void handleBatHit(Actor bat) {
        int relativeIntersectY = bat.getY() - ball.getY();
        double normalizedRelativeIntersectionY = (relativeIntersectY / (double) (bat.getImage().getHeight() / 2));
        double bounceAngle = normalizedRelativeIntersectionY * BALL_MAX_BOUNCE_ANGLE; // angle is limited to [-BALL_MAX_BOUNCE_ANGLE|BALL_MAX_BOUNCE_ANGLE]

        double cos = Math.cos(Math.toRadians(bounceAngle));
        dx = BALL_SPEED * cos * (dx < 0 ? 1 : -1);

        double sin = Math.sin(Math.toRadians(bounceAngle));
        dy = (BALL_SPEED * -sin);

        // Make sure the ball wont get stuck in the paddle
        do {
            ball.setLocation(ball.getActualX() + dx, ball.getActualY() + dy);
        } while (ball.getIntesecting(bat.getClass()) != null);
    }

    private void resetBall() {
        ball.setLocation(getWidth() / 2, getHeight() / 2);
        dx = (r.nextBoolean() ? BALL_SPEED : -BALL_SPEED); // randomly choose starting direction on x-Axis
        dy = 0;
    }

    private void handleClientTasks() throws Exception {
        clientConnection.sendUpdate(new PongClientData(bat.getX(), bat.getY()));
        PongHostData data = clientConnection.getRecentHostData();
        if (data == null) return;
        this.ball.setLocation(data.getBallPos().x, data.getBallPos().y);
        this.remote.setLocation(data.getBatPos().x, data.getBatPos().y);

        if (!data.getCurrentSoundFile().equals("")) { // Play the current soundfile if there is one
            Greenfoot.playSound(data.getCurrentSoundFile());
        }

        if (data.getBoosterPos().x == -1 && booster != null) { // Kein Booster mehr in der Welt des Hosts
            removeObject(booster);
            booster = null;
        } else if (booster == null && data.getBoosterPos().x > 0) { // Neuer Booster in der Welt des Hosts
            booster = new Booster();
            addObject(booster, data.getBoosterPos().x, data.getBoosterPos().y);
        } else if (booster != null && data.getBoosterPos().x > 0) { // Position des aktuellen Boosters hat sich geändert
            booster.setLocation(data.getBoosterPos().x, data.getBoosterPos().y);
        }
        showScoreboard(data.getHostPoints(), data.getClientPoints());
    }

    private void showScoreboard(int host, int client) {
        showText("" + host, BAT_WALL_OFFSET + 20, 50);
        showText("" + client, getWidth() - BAT_WALL_OFFSET - 20, 50);
    }
}