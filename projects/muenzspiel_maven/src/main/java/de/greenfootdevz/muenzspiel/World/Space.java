package de.greenfootdevz.muenzspiel.World;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import de.greenfootdevz.muenzspiel.GameInitFrame;
import de.greenfootdevz.muenzspiel.Actor.Coin;
import de.greenfootdevz.muenzspiel.Actor.SerialCoin;
import de.greenfootdevz.muenzspiel.Network.MuenzspielClientConnection;
import de.greenfootdevz.muenzspiel.Network.MuenzspielClientData;
import de.greenfootdevz.muenzspiel.Network.MuenzspielHostConnection;
import de.greenfootdevz.muenzspiel.Network.MuenzspielHostData;
import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Space extends World{
	
	private final int COIN_COUNT = 60;
	
	
	private boolean isHost;
	
	private MuenzspielClientConnection client;
	private MuenzspielHostConnection host;
	
	private List<SerialCoin> collected, enemyCollected;
	
	public Space() {
		super(960, 620, 1);
		this.collected = new ArrayList<>();
		this.enemyCollected = new ArrayList<SerialCoin>();
		InetAddress address = null;
		int port = 0;
		
		try {
			GameInitFrame frame = new GameInitFrame();
			frame.setVisible(true);
			Thread.sleep(100);
			isHost = frame.isHost();
			
			address = frame.getSelectedAddress();
			port = frame.getSelectedPort();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		if (isHost) {
			System.out.println("Hosting a game on this machine!");
			try {
				host = new MuenzspielHostConnection(address, 0, port, true);
				host.waitForClientConnection();
				
				createCoins();
				Thread.sleep(250);
				
				List<SerialCoin> coins = new ArrayList<SerialCoin>();
				List<Coin> mapCoins = getObjects(Coin.class);
				
				for(Coin c: mapCoins){
					coins.add(new SerialCoin(c));
				}
				
				host.sendUpdate(new MuenzspielHostData(coins, true));
				Thread.sleep(250);
				host.start();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Hosting client on this machine. Connection to -> " + address.toString() + ":" + port);
			
			try {
				client = new MuenzspielClientConnection(address, port, true);
//				Thread.sleep(500);
//				client.start();
				
				MuenzspielHostData data = client.read();
				while(!data.isInitial()){
					data = client.read();
				}
				
				for (SerialCoin c : data.getCoins()) {
					addObject(new Coin(c.getValue()), c.getX(), c.getY());
				}
				client.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createCoins() {
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
				if (!(c.isTouchingOtherCoin() || xPos - imageRadius < 0 || xPos + imageRadius > getWidth()
						|| yPos - imageRadius < 0 || yPos + imageRadius > getHeight())) {
					i++;
				} else {
					removeObject(c);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void showEndScreen(){
		int score = 0, enemyScore = 0;
		
		for(SerialCoin collectedS: collected){
			score += collectedS.getValue();
		}
		
		for(SerialCoin collectedE: enemyCollected){
			enemyScore += collectedE.getValue();
		}
		
		this.showText("Congratulations your Score is " + score + ", Enemyscore: " + enemyScore, getWidth() / 2, getHeight() / 2);
	}
	
	public void act() {
		// Network

		if (isHost) {
			executeHostTask();
		} else {
			executeClientTask();
		}

		if ((getObjects(Coin.class).size() == 0)){
			showEndScreen();
			Greenfoot.stop();
		}
	}
	
	private void executeHostTask(){
		MuenzspielClientData data = host.getMostRecent();
		if(data != null){
			SerialCoin[] all = data.getCoins();
			
			for(SerialCoin c : all){
				List<Coin> coins = getObjectsAt(c.getX(), c.getY(), Coin.class);
				if(coins.size() > 0){
					enemyCollected.add(new SerialCoin(coins.get(0)));
					removeObject(coins.get(0));
				}
			}
		}

		try {
			host.sendUpdate(new MuenzspielHostData(collected));
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private void executeClientTask(){
		MuenzspielHostData data = client.getRecentHostData();
		if(data != null){
			if(!data.isInitial()){
				SerialCoin[] all = data.getCoins();
				for(SerialCoin c : all){
					List<Coin> coins = getObjectsAt(c.getX(), c.getY(), Coin.class);
					if(coins.size() > 0){
						enemyCollected.add(new SerialCoin(coins.get(0)));
						removeObject(coins.get(0));
					}
				}
			}
		}
		
		try{
			SerialCoin[] send = collected.toArray(new SerialCoin[collected.size()]);
			client.sendUpdate(new MuenzspielClientData(send));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addCollected(Coin c) {
		this.collected.add(new SerialCoin(c));
	}

}