package de.greenfootdevz.networkpong.Network;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NetworkConfigManager {
    /**
     * reads a xml config file from path and creates a NetworkConfig object containing the data.
     */
    public static NetworkConfig getConfig(String path) {
        try {
            JAXBContext jc = JAXBContext.newInstance(NetworkConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

//            Marshaller marshaller = jc.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            NetworkConfig testconfig = new NetworkConfig();
//            testconfig.setPort(25566);
//            marshaller.marshal(testconfig, System.out);


            File xml = new File(path);
            if(xml.createNewFile()) {
                // There was no config file
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<networkConfig>\n" +
                        "    <port>25566</port>\n" +
                        "</networkConfig>");
                writer.close();
            }
            return (NetworkConfig) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Failed to read config file at: " + path, "Failed to read config file", JOptionPane.ERROR_MESSAGE);
            System.out.println("Failed to read config file at: " + path);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to create config file at: " + path, "Failed to create config file", JOptionPane.ERROR_MESSAGE);
            System.out.println("Failed to create config file at: " + path);
            e.printStackTrace();
        }
        return new NetworkConfig(); // Return default config
    }
}
