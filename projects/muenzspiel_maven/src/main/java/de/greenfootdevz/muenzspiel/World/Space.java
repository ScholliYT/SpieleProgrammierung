package de.greenfootdevz.muenzspiel.World;

import java.awt.Point;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.greenfootdevz.muenzspiel.GameInitFrame;
import de.greenfootdevz.muenzspiel.Actor.Coin;
import de.greenfootdevz.muenzspiel.Network.MuenzspielClientConnection;
import de.greenfootdevz.muenzspiel.Network.MuenzspielClientData;
import de.greenfootdevz.muenzspiel.Network.MuenzspielHostConnection;
import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Space extends World{
	
	private final int TIME_IN_SEC = 30;
	private final int COIN_COUNT = 60;

	private long startTime = System.currentTimeMillis();
	
	private boolean isHost;
	
	private MuenzspielClientConnection client;
	private MuenzspielHostConnection host;
	
	private List<Coin> coins;
	
	private int collectedValue = 0;
	
	public Space(){
		super(960, 620, 1);
		this.coins = new ArrayList<Coin>();
		
		InetAddress address = null;
		int port = 0;
		
		try{
			GameInitFrame frame = new GameInitFrame();
			frame.setVisible(true);
			Thread.sleep(100);
			isHost = frame.isHost();
			
			address = frame.getSelectedAddress();
			port = frame.getSelectedPort();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		if(isHost){
			System.out.println("Hosting a game on this machine!");
			try{
				host = new MuenzspielHostConnection(address, 0, port, true);
				host.waitForClientConnection();
				
				createCoins();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			System.out.println("Hosting client on this machine. Connection to -> " + address.toString() + ":" + port);
			
			try{
				client = new MuenzspielClientConnection(address, port, true);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
//		createCoins();
	}
		
	public void createCoins(){
		try {
			for (int i = 0; i < COIN_COUNT;) {
				int xPos = Greenfoot.getRandomNumber(getWidth());
				int yPos = Greenfoot.getRandomNumber(getHeight());
				
				// randomly get the value of that coin
				int randomNumber = Greenfoot.getRandomNumber(100);
				int coinValue = 1;
				if (randomNumber < 10) {
					coinValue = 3;
				} else if (randomNumber < 40) {
					coinValue = 2;
				}
				
				Coin c = new Coin(coinValue);
				addObject(c, xPos, yPos);
				int imageRadius = c.getImage().getWidth() / 2;
				
				// check for valid position of new coin
				if (!(c.isTouchingOtherCoin() || xPos - imageRadius < 0 || xPos + imageRadius > getWidth() || yPos - imageRadius < 0 || yPos + imageRadius > getHeight())) {
					coins.add(c);
					i++;
				}else{
					removeObject(c);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void evaluation(){
		showEndScreen(collectedValue);
	}
	
	private void showEndScreen(int score) {
		this.showText("Congratulations your Score is " + score, getWidth() / 2, getHeight() / 2);
	}
	
	private void showTime(int time) {
		this.showText("Verbleibende Zeit: " + time, 150, this.getHeight() - 20);
	}
	
	public void act(){
		int remainingTime = (int) (TIME_IN_SEC - ((System.currentTimeMillis() - startTime) / 1000));
		showTime(remainingTime);
		
		//Network
		
		if(isHost){
			executeHostTask();
		}else{
			executeClientTask();
		}
		
		if (remainingTime <= 0 || getObjects(Coin.class).size() == 0){
			evaluation();
			Greenfoot.stop();
		}
	}
	
	private void executeHostTask(){
		MuenzspielClientData data = host.getMostRecent();
		
		for(Point p: data.getPoints()){
			List<Coin> coins = getObjectsAt(p.x, p.y, Coin.class);
			if(coins.size() > 0){
				Coin c = coins.get(0);
				
			}
		}
		
	}
	
	private void executeClientTask(){
		List<Coin> collected = getCollectedCoins();
		List<Point> points = new ArrayList<Point>();
		
		for(Coin c: collected){
			points.add(new Point(c.getX(), c.getY()));
			collectedValue += c.getValue();
		}
		try{
			client.sendUpdate(new MuenzspielClientData(points.toArray(new Point[points.size()])));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		client.getRecentHostData();
		
		
		
	}
	
	private List<Coin> getCollectedCoins(){
		List<Coin> onMap = getObjects(Coin.class);
		List<Coin> result = new ArrayList<Coin>();
		
		for(Coin c: coins){
			result.add(c);
		}
		
		result.removeIf(value -> {
			return onMap.contains(value);
		});
		this.coins = onMap;
		return result;
	}
	
}