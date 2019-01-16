import java.awt.Point;
import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Host zum Client gesendet wird.
 */
public class PongHostData implements Serializable{
	
	private static final long serialVersionUID = 4498114982435349255L;
	
	private int ballX, ballY;
	private int batX, batY;
	
	public PongHostData(int batX, int batY, int ballX, int ballY){
		this.ballX = ballX;
		this.ballY = ballY;
		this.batX = batX;
		this.batY = batY;
	}
	
	public Point getBallPos(){
		return new Point(ballX, ballY);
	}
	
	public Point getBatPos(){
		return new Point(batX, batY);
	}
	
}