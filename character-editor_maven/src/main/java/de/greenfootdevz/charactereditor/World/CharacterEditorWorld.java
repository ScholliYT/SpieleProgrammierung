package de.greenfootdevz.charactereditor.World;

import de.greenfootdevz.charactereditor.Actor.BodyPart;
import de.greenfootdevz.charactereditor.Actor.BodyPartSelector;
import de.greenfootdevz.charactereditor.ExceptionDialog;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import greenfoot.util.GreenfootUtil;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import static greenfoot.Greenfoot.isKeyDown;

public class CharacterEditorWorld extends World {

    public static final String BODYPARTS_ROOTDIR = "bodyparts/";
    private final BodyPart[] bodyParts;
    private int currentBodyPartIndex;
    private long lastButtonPressed = 0;
    private final long BUTTON_PRESS_DELAY = 300;

    public CharacterEditorWorld() {
        super(1000, 800, 1);

        ArrayList<BodyPart> bodyParts = new ArrayList<>();
        addBodyPartIfPossible(bodyParts, "body", 0, 0);
        addBodyPartIfPossible(bodyParts, "head", 0, 0);
        addBodyPartIfPossible(bodyParts, "hair", 0, 0);
        addBodyPartIfPossible(bodyParts, "eyes", 0, 0);
        addBodyPartIfPossible(bodyParts, "nose", 0, 0);
        addBodyPartIfPossible(bodyParts, "mouth", 0, 0);

        this.bodyParts = bodyParts.toArray(new BodyPart[0]);

        // BodyPartSelector bpstest = new BodyPartSelector(this.bodyParts[0]);
        // bpstest.addObjects(this, 500, 300);

        Point generalOffset = new Point(200, 750 / 2 + 25);
        BodyPart[] bodyParts1 = this.bodyParts;
        for (int i = 0; i < bodyParts1.length; i++) {
            BodyPart bp = bodyParts1[i];
            addObject(bp, generalOffset.x + bp.getOffset().x, generalOffset.y + bp.getOffset().y);


            BodyPart bpclone = bp.clone();
            bpclone.setPreview(true);
            BodyPartSelector bps = new BodyPartSelector(bp.clone());
            bps.addObjects(this, generalOffset.x + 300, 100 + i * 100);
        }


        currentBodyPartIndex = 0;
        System.out.println("Starting the Game!");
        Greenfoot.start();
    }

    private void addBodyPartIfPossible(ArrayList<BodyPart> bodyParts, String bodyPartName, int xoffset, int yoffset) {
        BodyPart bodyPart = initializeBodyPart(bodyPartName, new Point(xoffset, yoffset));
        if (bodyPart != null) {
            bodyParts.add(bodyPart);
        }
    }

    private GreenfootImage[] getImages(String partName) {
        return loadImages(partName, (dir, name) -> (name.endsWith(".png") || name.endsWith(".jpg")) && !name.endsWith("_prev.png") && !name.endsWith("_prev.jpg")
        );
    }

    private GreenfootImage[] getPrevImages(String partName) {
        return loadImages(partName, (dir, name) -> (name.endsWith(".png") || name.endsWith(".jpg")) && (name.endsWith("_prev.png") || name.endsWith("_prev.jpg"))
        );
    }



    private GreenfootImage[] loadImages(String partName, FilenameFilter filefilter) {
        URL bodyPartRoot = null;
        try {
            bodyPartRoot = GreenfootUtil.getURL(BODYPARTS_ROOTDIR + partName, "images");
        } catch (FileNotFoundException e1) {
            new ExceptionDialog(e1);
        }
        File bodyPartRootFile = new File(bodyPartRoot.getPath().replaceAll("%20", " ")); //Anscheinend gibt es ein Problem mit Leerzeichen im Pfad
        if (!bodyPartRootFile.isDirectory()) {
            try {
                throw new IOException("Directory: " + bodyPartRoot.getPath() + " was not found.");
            } catch (IOException e1) {
                new ExceptionDialog(e1);
            }
        }


        File[] bodyPartImages = bodyPartRootFile.listFiles(filefilter);


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
            images[i] = new GreenfootImage(BODYPARTS_ROOTDIR + partName + "/" + bodyPartImages[i].getName());
        }
        return images;
    }

    private BodyPart initializeBodyPart(String bodyPartName, Point offset) {

        BodyPart bodyPart = null;
        try {
            bodyPart = new BodyPart(bodyPartName, getImages(bodyPartName), getPrevImages(bodyPartName), offset, false);
        } catch (IllegalArgumentException e1) {
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
        if (currentBodyPartIndex <= 0) {
            currentBodyPartIndex = bodyParts.length - 1;
        }
        showText(bodyParts[currentBodyPartIndex].getName(), 500, 500);
    }

    private void nextBodyPart() {
        currentBodyPartIndex++;
        if (currentBodyPartIndex >= bodyParts.length) {
            currentBodyPartIndex = 0;
        }
        showText(bodyParts[currentBodyPartIndex].getName(), 500, 500);
    }

}