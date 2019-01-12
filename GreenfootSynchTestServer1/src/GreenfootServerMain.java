
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class GreenfootServerMain{
	
	private static String key;
	
	public static void main(String[] args){
		try{
			NetworkingDevices devices = new NetworkingDevices();
			
			out.println("Wähle ein Netzwerkinterface, indem du die entsprechde ID ins Textfeld eingibst:");
			devices.printAvailableInterfaces(System.out);
			NetworkInterface networkInterface = null;
			Scanner s = new Scanner(System.in);
			while(networkInterface == null){
				String in = s.nextLine();
				try{
					int id = Integer.parseInt(in);
					networkInterface = devices.getInterface(id);
					if(networkInterface == null){
						System.out.println("Gib bitte eine gültige ID ein:");
					}
					continue;
				}catch(Exception e){}
				System.out.println("Gib bitte eine gültige Nummer ein:");
			}
			s.close();
			
			//Theoretisch müsste anhand der Addresse das richtige Interface verwendet werden
			
			out.println("Connectaddresse: " + devices.getIPv4AddressFor(networkInterface));
			key = getRandomAccessKey(3);
			out.println("Accesskey: " + key);
			
			ServerSocket server = new ServerSocket(25566, 0, InetAddress.getByName(devices.getIPv4AddressFor(networkInterface)));
			
			final int TARGET_TICK_RATE = 60;
			
			ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
			
			GameServer serverInstance = new GameServer(server);
			ScheduledFuture<?> future = service.scheduleAtFixedRate(serverInstance, 0, 1000 / TARGET_TICK_RATE, TimeUnit.MILLISECONDS);
			
			ConnectionAcceptor acceptor = new ConnectionAcceptor(server, key);
			acceptor.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getRandomAccessKey(int length){
		Random random = new Random();
		char[] key = new char[length];
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		for(int i = 0; i < length; i++){
			key[i] = chars[random.nextInt(chars.length)];
		}
		return new String(key);
	}
	
}