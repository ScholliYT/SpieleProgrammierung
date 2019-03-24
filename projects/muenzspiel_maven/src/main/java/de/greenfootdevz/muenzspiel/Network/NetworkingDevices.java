package de.greenfootdevz.muenzspiel.Network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class NetworkingDevices {

	private Enumeration<NetworkInterface> interfaces;
	private HashMap<Integer, NetworkInterface> availableDevices;

	public NetworkingDevices() throws SocketException {
		this.interfaces = NetworkInterface.getNetworkInterfaces();
		this.availableDevices = new HashMap<>();
		int count = 0;
		for (NetworkInterface i : Collections.list(interfaces)) {
			if (i == null)
				continue;
			address: for (InetAddress add : Collections.list(i.getInetAddresses())) {
				if (add instanceof Inet4Address) {
					availableDevices.put(++count, i);
					break address;
				}
			}
		}
	}

	public void reload() throws SocketException {
		this.interfaces = NetworkInterface.getNetworkInterfaces();

		for (NetworkInterface i : Collections.list(interfaces)) {
			int count = 0;
			if (i.getInetAddresses().hasMoreElements()) {
				availableDevices.put(++count, i);
			}
		}
	}

	public String[] getAvailableInterfaces() throws Exception {
		String[] result = new String[availableDevices.size()];
		for (int i = 1; i < result.length + 1; i++) { // Erster Eintrag immer null!
			NetworkInterface interf = availableDevices.get(i);
			if (interf == null)
				continue;
			result[i - 1] = interf.getDisplayName() + " [" + getIPv4AddressFor(interf) + "]";
		}
		return result;
	}

	public String getIPv4AddressAsStringFor(NetworkInterface i) {
		String ip = "unknown";
		for (InetAddress address : Collections.list(i.getInetAddresses())) {
			if (address instanceof Inet4Address) {
				ip = address.getHostAddress();
				break;
			}
		}
		return ip;
	}

	public InetAddress getIPv4AddressFor(NetworkInterface i) throws Exception {
		return InetAddress.getByName(getIPv4AddressAsStringFor(i));
	}

	public NetworkInterface getInterface(int id) {
		if (availableDevices.containsKey(id)) {
			return availableDevices.get(id);
		}
		return null;
	}

}