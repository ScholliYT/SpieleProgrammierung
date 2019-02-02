package de.greenfootdevz.charactereditor.World;

import com.sun.javaws.exceptions.InvalidArgumentException;
import de.greenfootdevz.charactereditor.Actor.Ball;
import de.greenfootdevz.charactereditor.Actor.BodyPart;
import de.greenfootdevz.charactereditor.ExceptionDialog;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import greenfoot.util.GreenfootUtil;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;

import static greenfoot.Greenfoot.isKeyDown;

public class CharacterEditorWorld extends World {

    private final BodyPart[] bodyParts;
    private int currentBodyPartIndex;
    private long lastButtonPressed = 0;
    private final long BUTTON_PRESS_DELAY = 300;

    private void addBodyPartIfPossible(ArrayList<BodyPart> bodyParts, String bodyPartName, int xoffset, int yoffset) {
        BodyPart bodyPart = initializeBodyPart(bodyPartName, new Point(xoffset, yoffset));
        if (bodyPart != null) {
            bodyParts.add(bodyPart);
        }
    }

    public CharacterEditorWorld() {
        super(1000, 800, 1);

        ArrayList<BodyPart> bodyParts = new ArrayList<>();
        addBodyPartIfPossible(bodyParts, "body", 0, 0);
        addBodyPartIfPossible(bodyParts, "head", 0, -200);
        addBodyPartIfPossible(bodyParts, "mouth", 0, -170);
        addBodyPartIfPossible(bodyParts, "nose", 0, -200);
        addBodyPartIfPossible(bodyParts, "eyes", 0, -215);
        addBodyPartIfPossible(bodyParts, "hair/Farbe", 0, -240);

        this.bodyParts = bodyParts.toArray(new BodyPart[0]);

        Point generalOffset = new Point(200, 350);
        for (BodyPart bp : this.bodyParts) {
            addObject(bp, generalOffset.x + bp.getOffset().x, generalOffset.y + bp.getOffset().y);
        }
        currentBodyPartIndex = 0;
        System.out.println("Starting the Game!");
        Greenfoot.start();
    }

    private GreenfootImage[] getBodyPartImages(String name) {
        URL bodyPartRoot = null;
        try {
            bodyPartRoot = GreenfootUtil.getURL("body_parts/" + name, "images");
        } catch (FileNotFoundException e1) {
            new ExceptionDialog(e1);
        }
        File bodyPartRootFile = new File(bodyPartRoot.getPath());

        if (!bodyPartRootFile.isDirectory()) {
            try {
                throw new IOException("Directory: " + bodyPartRoot.getPath() + " was not found.");
            } catch (IOException e1) {
                new ExceptionDialog(e1);
            }
        }


        File[] bodyPartImages = bodyPartRootFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png") || name.endsWith(".jpg");
            }
        });

        if (bodyPartImages == null) {
            try {
                throw new IOException("Error while reading files in directory: " + bodyPartRootFile.getPath());
            } catch (IOException e1) {
                new ExceptionDialog(e1);
            }
        }


        int n = bodyPartImages.length;
        GreenfootImage[] images = new GreenfootImage[n];
        for (int i = 0; i < n; i++) {
            images[i] = new GreenfootImage("body_parts/" + name + "/" + bodyPartImages[i].getName());
        }
        return images;
    }

    private BodyPart initializeBodyPart(String name, Point offset) {

        BodyPart bodyPart = null;
        try {
            bodyPart = new BodyPart(name, getBodyPartImages(name), offset);
        } catch (InvalidArgumentException e1) {
            new ExceptionDialog(e1);
        }
        return bodyPart;
    }


    public void act() {
        if (lastButtonPressed < System.currentTimeMillis() - BUTTON_PRESS_DELAY) {
            if (isKeyDown("left")) {
                bodyParts[currentBodyPartIndex].previous();
                lastButtonPressed = System.currentTimeMillis();
            } else if (isKeyDown("right")) {
                bodyParts[currentBodyPartIndex].next();
                lastButtonPressed = System.currentTimeMillis();
            } else if (isKeyDown("up")) {
                nextBodyPart();
                lastButtonPressed = System.currentTimeMillis();
            } else if (isKeyDown("down")) {
                previousBodyPart();
                lastButtonPressed = System.currentTimeMillis();
            }
        }
    }

    private void previousBodyPart() {
        currentBodyPartIndex--;
        if(currentBodyPartIndex <= 0) {
            currentBodyPartIndex = bodyParts.length - 1;
        }
        showText(bodyParts[currentBodyPartIndex].getName(), 500,500);
    }

    private void nextBodyPart() {
        currentBodyPartIndex++;
        if(currentBodyPartIndex >= bodyParts.length) {
            currentBodyPartIndex = 0;
        }
        showText(bodyParts[currentBodyPartIndex].getName(), 500,500);
    }

}