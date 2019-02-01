package de.greenfootdevz.charactereditor.World;

import de.greenfootdevz.charactereditor.*;
import de.greenfootdevz.charactereditor.Actor.Ball;
import de.greenfootdevz.charactereditor.Actor.Bat;
import de.greenfootdevz.charactereditor.Actor.Booster;
import de.greenfootdevz.charactereditor.Actor.RemoteBat;
import de.greenfootdevz.charactereditor.Network.PongClientConnection;
import de.greenfootdevz.charactereditor.Network.PongClientData;
import de.greenfootdevz.charactereditor.Network.PongHostConnection;
import de.greenfootdevz.charactereditor.Network.PongHostData;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

import java.net.InetAddress;
import java.util.Random;

public class CharacterEditorWorld extends World{


    public CharacterEditorWorld() {
        super(1000, 500, 1);
        
        //exception(null); //Throws exception in order to test dialog

        addObject(new Ball(), 50, 50);
        
        
        System.out.println("Starting the Game!");
        Greenfoot.start();
    }

    public void act(){

    }

}