package de.greenfootdevz.muenzspiel.Network;

import java.awt.Point;
import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Client zum Host
 * gesendet wird.
 */
public class MuenzspielClientData implements Serializable{
	
	private static final long serialVersionUID = -6243741163353349874L;
	
	private final int[] xes;
	private final int[] yes;
	
	public MuenzspielClientData(Point[] points){
		xes = new int[points.length];
		yes = new int[points.length];
		
		for(int i = 0; i < points.length; i++){
			xes[i] = points[i].x;
			yes[i] = points[i].y;
		}
	}
	
	public Point[] getPoints(){
		Point[] result = new Point[xes.length];
		
		for(int i = 0; i < xes.length; i++){
			result[i] = new Point(xes[i], yes[i]);
		}
		return result;
	}
	
}