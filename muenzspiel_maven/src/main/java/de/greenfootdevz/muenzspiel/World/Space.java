package de.greenfootdevz.muenzspiel.World;

import de.greenfootdevz.muenzspiel.Actor.Coin;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Space extends World {
    private final int TIME_IN_SEC = 30;
    private final int COIN_COUNT = 60;

    private long startTime = System.currentTimeMillis();

    public Space() {
        super(960, 620, 1);

        createCoins();
    }


    public void createCoins() {
        try {
            for (int i = 0; i < COIN_COUNT; i++) {
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

                Coin c = new Coin(Color.CYAN, coinValue);
                addObject(c, xPos, yPos);
                int imageRadius = c.getImage().getWidth() / 2;

                // check for valid position of new coin
                if (c.isTouchingOtherCoin() || xPos - imageRadius < 0 || xPos + imageRadius > getWidth() || yPos - imageRadius < 0 || yPos + imageRadius > getHeight()) {
                    removeObject(c);
                    i--;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void evaluation() {
        int score = 0;
        for (Coin c : this.getObjects(Coin.class)) {
            if (c.isClicked()) {

                score += c.getValue();
            }
        }
        showEndScreen(score);
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

        if (remainingTime <= 0) {
            evaluation();
            Greenfoot.stop();
        }
    }
}