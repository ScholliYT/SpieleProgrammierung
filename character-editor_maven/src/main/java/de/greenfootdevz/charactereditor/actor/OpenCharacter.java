package de.greenfootdevz.charactereditor.actor;

import de.greenfootdevz.charactereditor.util.IOpenCharacter;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenCharacter extends Actor {

    private IOpenCharacter openCharacter;

    public OpenCharacter(int width, int height, IOpenCharacter openCharacter) {
        this.openCharacter = openCharacter;
        setImage("controls/openfile.png");
        getImage().scale(width, height);

    }

    public OpenCharacter(IOpenCharacter openCharacter) {
        this(100, 100, openCharacter);
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            JSONParser jsonParser = new JSONParser();
            File characterFile = new File(System.getProperty("user.home") + "/charactereditor" + "/character.json");
            if (characterFile.exists() && characterFile.isFile()) {
                try (FileReader reader = new FileReader(characterFile)) {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);

                    JSONObject jsonObject = (JSONObject) obj;

                    openCharacter.loadBodyParts(jsonObject);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Es konnte kein Charakter gefunden werden");
            }
        }
    }
}