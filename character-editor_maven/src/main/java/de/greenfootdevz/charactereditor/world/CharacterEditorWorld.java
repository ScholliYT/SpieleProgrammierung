package de.greenfootdevz.charactereditor.world;

import de.greenfootdevz.charactereditor.actor.BodyPart;
import de.greenfootdevz.charactereditor.actor.BodyPartSelector;
import de.greenfootdevz.charactereditor.actor.PepePls;
import de.greenfootdevz.charactereditor.util.ExceptionDialog;
import de.greenfootdevz.charactereditor.GreenfootImageExtended;
import de.greenfootdevz.charactereditor.util.ImageLoader;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterEditorWorld extends World {
    private final BodyPart[] bodyParts;


    public CharacterEditorWorld() {
        super(1000, 800, 1);


        this.bodyParts = initBodyParts("body", "head", "hair", "eyes", "nose", "mouth");

        // Add Body part selector
        Point generalOffset = new Point(200, 750 / 2 + 25);
        BodyPart[] bodyParts1 = this.bodyParts;
        for (int i = 0; i < bodyParts1.length; i++) {
            BodyPart bp = bodyParts1[i];
            addObject(bp, generalOffset.x, generalOffset.y);

            BodyPart bpClone = bp.clone();
            bpClone.setPreview(true);
            BodyPartSelector bps = new BodyPartSelector(bpClone, bp);
            bps.addObjects(this, generalOffset.x + 300, 100 + i * 100);
        }

        initPepe();

        Greenfoot.start();
    }

    private BodyPart[] initBodyParts(String... parts) {
        ArrayList<BodyPart> bodyParts = new ArrayList<>();
        for (String part : parts) {
            addBodyPartIfPossible(bodyParts, part);
        }
        return bodyParts.toArray(new BodyPart[0]);
    }

    private void initPepe() {
        int pepeWidth = 100, pepeHeight = 100;
        PepePls pepe = new PepePls(pepeWidth, pepeHeight);
        addObject(pepe, getWidth() - pepeWidth / 2, getHeight() - pepeHeight / 2);
    }

    public void act() {

    }

    private void addBodyPartIfPossible(ArrayList<BodyPart> bodyParts, String bodyPartName) {
        BodyPart bodyPart = initializeBodyPart(bodyPartName);
        if (bodyPart != null) {
            bodyParts.add(bodyPart);
        }
    }

    private BodyPart initializeBodyPart(String bodyPartName) {

        BodyPart bodyPart = null;
        try {
            GreenfootImage[] images = ImageLoader.getImages(bodyPartName);
            GreenfootImage[] previmages = new GreenfootImage[images.length];
            for (int i = 0; i < images.length; i++) {
                GreenfootImage image = images[i];
                String filename = ((GreenfootImageExtended) image).getFilename();
                GreenfootImage prevImage = ImageLoader.getPrevImage(bodyPartName, filename);
                previmages[i] = prevImage;
            }
            bodyPart = new BodyPart(bodyPartName, images, previmages, false);
        } catch (IllegalArgumentException e1) {
            new ExceptionDialog(e1);
        }
        return bodyPart;
    }


    public void saveCharacter() {
        JSONObject jsonObject = new JSONObject();
        for (BodyPart bp : bodyParts) {
            try {
                jsonObject.put(bp.getName(), bp.getCurrentImage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            File path = new File(System.getProperty("user.home") + "/charactereditor");
            path.mkdirs();
            FileOutputStream fos = new FileOutputStream(new File(path + "/character.json"));
            fos.write(jsonObject.toString(1).getBytes());

            // Save as new image
            ImageIO.write(combineImages(), "PNG", new File(path.toString() + "/character.png"));

            JOptionPane.showMessageDialog(null, "Datei gespeichert!");

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage combineImages() {
        // create the new image, canvas size is defined by the first image
        int w = bodyParts[0].getCurrentImage().getAwtImage().getWidth();
        int h = bodyParts[0].getCurrentImage().getAwtImage().getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint all images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        for (BodyPart bp : bodyParts) {
            g.drawImage(bp.getCurrentImage().getAwtImage(), 0, 0, null);
        }
        return combined;
    }
}