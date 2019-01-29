import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class NetworkConfigManager {
    /**
     * reads a xml config file from path and creates a NetworkConfig object containing the data.
     */
    public static NetworkConfig readNetworkConfig(String path) {
        try {
            JAXBContext jc = JAXBContext.newInstance(NetworkConfig.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

//            Marshaller marshaller = jc.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            NetworkConfig testconfig = new NetworkConfig();
//            testconfig.setPort(25566);
//            marshaller.marshal(testconfig, System.out);


            File xml = new File(path);
            return (NetworkConfig) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            JOptionPane.showMessageDialog(null, "Failed to read config file at: " + path, "Failed to read config file", JOptionPane.ERROR_MESSAGE);
            System.out.println("Failed to read config file at: " + path);
            e.printStackTrace();
        }
        return new NetworkConfig(); // Return default config
    }

}
