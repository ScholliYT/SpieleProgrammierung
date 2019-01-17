import java.awt.Point;
import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Host zum Client gesendet wird.
 */
public class PongHostData implements Serializable{
	
	private static final long serialVersionUID = 4498114982435349255L;
	
	private int ballX, ballY;
	private int batX, batY;
	private int boosterX, boosterY;
	private int pointHost, pointClient;
	
	public PongHostData(int batX, int batY, int ballX, int ballY, int boosterX, int boosterY,
			int pointsHost, int pointsClient){
		this.ballX = ballX;
		this.ballY = ballY;
		this.batX = batX;
		this.batY = batY;
		this.boosterX = boosterX;
		this.boosterY = boosterY;
		this.pointHost = pointsHost;
		this.pointClient = pointsClient;
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
	
}