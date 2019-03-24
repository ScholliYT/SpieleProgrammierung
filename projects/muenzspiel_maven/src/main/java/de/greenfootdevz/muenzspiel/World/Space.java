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

public class Space extends World {

	private final int TIME_IN_SEC = 30;
	private final int COIN_COUNT = 60;

	private long startTime = System.currentTimeMillis();

	private boolean isHost, initialUpdated;

	private MuenzspielClientConnection client;
	private MuenzspielHostConnection host;

	private List<Coin> collectedInTick;

	private int collectedValue = 0;

	public Space() {
		super(960, 620, 1);
		this.collectedInTick = new ArrayList<Coin>();
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
				host.sendUpdate(new MuenzspielHostData(getObjects(Coin.class), true));
				Thread.sleep(10000);
				host.start();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			this.initialUpdated = true;
		} else {
			System.out.println("Hosting client on this machine. Connection to -> " + address.toString() + ":" + port);

			try {
				client = new MuenzspielClientConnection(address, port, true);
				Thread.sleep(500);
				client.start();
				
				MuenzspielHostData data = client.getRecentHostData();
				while(!data.isInitial()){
					System.out.println("");
				}
				
				for (SerialCoin c : data.getCoins()) {
					addObject(new Coin(c.getValue()), c.getX(), c.getY());
				}
				this.initialUpdated = true;
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

	private void evaluation() {
		showEndScreen(collectedValue);
	}

	private void showEndScreen(int score) {
		this.showText("Congratulations your Score is " + score, getWidth() / 2, getHeight() / 2);
	}

	private void showTime(int time) {
		this.showText("Verbleibende Zeit: " + time, 150, this.getHeight() - 20);
	}

	public void act() {
		int remainingTime = (int) (TIME_IN_SEC - ((System.currentTimeMillis() - startTime) / 1000));
		showTime(remainingTime);

		// Network

		if (isHost) {
			executeHostTask();
		} else {
			executeClientTask();
		}

		if ((getObjects(Coin.class).size() == 0 && initialUpdated)) {
			evaluation();
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
					removeObject(coins.get(0));
				}
			}
		}

		try {
			host.sendUpdate(new MuenzspielHostData(collectedInTick));
		} catch (Exception e){}
//		collectedInTick.clear();
	}

	private void executeClientTask(){
		MuenzspielHostData data = client.getRecentHostData();
		System.out.println(data == null);
		if(data != null){
			if(!data.isInitial()){
				SerialCoin[] all = data.getCoins();
				for(SerialCoin c : all){
					List<Coin> coins = getObjectsAt(c.getX(), c.getY(), Coin.class);
					if(coins.size() > 0){
						removeObject(coins.get(0));
					}
				}
			}else{
				
			}
		}

		try{
			client.sendUpdate(new MuenzspielClientData((Coin[]) collectedInTick.toArray()));
		}catch(Exception e){}
//		collectedInTick.clear();

	}

	public void addCollected(Coin c) {
		this.collectedInTick.add(c);
	}

}