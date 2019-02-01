package de.greenfootdevz.charactereditor.Network;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NetworkConfig {
    private int Port = 25566; // default value is 25566

    public int getPort() {
        return Port;
    }

    public void setPort(int port) {
        this.Port = port;
    }

    @Override
    public String toString() {
        return "NetworkConfig{" +
                "Port=" + Port +
                '}';
    }
}
