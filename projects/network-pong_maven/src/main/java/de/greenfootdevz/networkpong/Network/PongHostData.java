package de.greenfootdevz.networkpong.Network;

import java.awt.Point;
import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Host zum Client gesendet wird.
 */
public class PongHostData implements Serializable{

	private static final long serialVersionUID = 4498114982435349255L;

	private final int ballX;
	private final int ballY;
	private final int batX;
	private final int batY;
	private final int boosterX;
	private final int boosterY;
	private final int pointHost;
	private final int pointClient;
	private final String currentSoundFile;

	public PongHostData(int batX, int batY, int ballX, int ballY, int boosterX, int boosterY,
			int pointsHost, int pointsClient, String currentSoundFile){
		this.ballX = ballX;
		this.ballY = ballY;
		this.batX = batX;
		this.batY = batY;
		this.boosterX = boosterX;
		this.boosterY = boosterY;
		this.pointHost = pointsHost;
		this.pointClient = pointsClient;
		this.currentSoundFile = currentSoundFile;
	}

	public Point getBallPos(){
		return new Point(ballX, ballY);
	}

	public Point getBatPos(){
		return new Point(batX, batY);
	}

	public Point getBoosterPos(){
		return new Point(boosterX, boosterY);
	}

	public int getHostPoints(){
		return pointHost;
	}

	public int getClientPoints(){
		return pointClient;
	}

	public String getCurrentSoundFile(){
		return currentSoundFile;
	}

}