import java.io.PrintStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;

public class NetworkingDevices{
	
	private Enumeration<NetworkInterface> interfaces;
	private HashMap<Integer, NetworkInterface> availableDevices;
	
	public NetworkingDevices() throws SocketException{
		this.interfaces = NetworkInterface.getNetworkInterfaces();
		this.availableDevices = new HashMap<>();
		for(NetworkInterface i: Collections.list(interfaces)){
			int count = 0;
			if(i.getInetAddresses().hasMoreElements()){
				availableDevices.put(++count, i);
			}
		}
	}
	
	public void reload() throws SocketException{
		this.interfaces = NetworkInterface.getNetworkInterfaces();
		
		for(NetworkInterface i: Collections.list(interfaces)){
			int count = 0;
			if(i.getInetAddresses().hasMoreElements()){
				availableDevices.put(++count, i);
			}
		}
	}
	
	public void printAvailableInterfaces(PrintStream out){
		out.format("%2s|%-50s|%-10s|%-20s\n", "ID", "Anzeigename", "Systemname", "Gerätaddresse");
		for(Entry<Integer, NetworkInterface> entry: availableDevices.entrySet()){
			NetworkInterface i = entry.getValue();
			out.format("%2d|%-50s|%-10s|%-20s\n", entry.getKey(), i.getDisplayName(), i.getName(), i.getInetAddresses().nextElement().getHostAddress());
		}
	}
	
	public NetworkInterface getInterface(int id){
		if(availableDevices.containsKey(id)){
			return availableDevices.get(id);
		}
		return null;
	}
	
}