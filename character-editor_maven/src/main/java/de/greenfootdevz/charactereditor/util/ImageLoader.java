package de.greenfootdevz.charactereditor.util;

import de.greenfootdevz.charactereditor.GreenfootImageExtended;
import de.greenfootdevz.charactereditor.world.CharacterEditorWorld;
import greenfoot.GreenfootImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

public class ImageLoader {
    private static final String BODYPARTS_ROOTDIR = "bodyparts/";

    public static GreenfootImageExtended[] getImages(String partName) {
        return loadImages(partName, (name) ->
                (name.endsWith(".png") || name.endsWith(".jpg")) && !name.endsWith("_prev.png") && !name.endsWith("_prev.jpg")
        );
    }

    public static GreenfootImageExtended getPrevImage(String partName, String filename) {
        filename = filename.substring(0, filename.indexOf('.')); // "picture.png" -> "picture"
        String finalFilename = filename;
        return loadImages(partName, (name) ->
                (name.endsWith(finalFilename + "_prev.png") || name.endsWith(finalFilename + "_prev.jpg"))
        )[0];
    }

    private static GreenfootImageExtended[] loadImages(String partName, IFileSelector fileSelector) {
        FileSystem fileSystem = null;
        try {
            String path = "/images/" + BODYPARTS_ROOTDIR + partName;
            URI uri = CharacterEditorWorld.class.getResource(path).toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath(path);
            } else {
                myPath = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(myPath, 1);
            Iterator<Path> it = walk.iterator();
            ArrayList<GreenfootImageExtended> images = new ArrayList<>();
            while (it.hasNext()) {
                Path currentPath = it.next();
                if (currentPath.toString().endsWith(".png") || currentPath.toString().endsWith(".jpg")) {
                    String filename = currentPath.getFileName().toString();
                    if(fileSelector.filter(filename)) {
                        BufferedImage bufImage = ImageIO.read(ImageLoader.class.getResourceAsStream(path + "/" + filename));

                        GreenfootImage gImage = new GreenfootImage(bufImage.getWidth(), bufImage.getHeight());
                        BufferedImage gBufImg = gImage.getAwtImage();
                        Graphics2D graphics = (Graphics2D) gBufImg.getGraphics();
                        graphics.drawImage(bufImage, null, 0, 0);

                        images.add(new GreenfootImageExtended(gImage, filename));
                    }
                }
            }
            GreenfootImageExtended[] imagesarray = new GreenfootImageExtended[images.size()];
            return images.toArray(imagesarray);
        } catch (Exception e) {
            new ExceptionDialog(e);
        }
        finally {
            if(fileSystem != null) {
                try {
                    fileSystem.close();
                } catch (IOException ignored) {}
            }
        }
        return new GreenfootImageExtended[0];
    }
}
