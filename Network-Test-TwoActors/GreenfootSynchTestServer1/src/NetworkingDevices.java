import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetAddress;
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
		int count = 0;
		for(NetworkInterface i: Collections.list(interfaces)){
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
			
			out.format("%2d|%-50s|%-10s|%-20s\n", entry.getKey(), i.getDisplayName(), i.getName(), getIPv4AddressFor(i));
		}
	}
	
	public String getIPv4AddressFor(NetworkInterface i){
		String ip = "unknown";
		for(InetAddress address: Collections.list(i.getInetAddresses())){
			if(address instanceof Inet4Address){
				ip = address.getHostAddress();
				break;
			}
		}
		return ip;
	}
	
	public NetworkInterface getInterface(int id){
		if(availableDevices.containsKey(id)){
			return availableDevices.get(id);
		}
		return null;
	}
	
}