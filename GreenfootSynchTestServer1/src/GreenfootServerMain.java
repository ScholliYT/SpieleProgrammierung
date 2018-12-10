
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GreenfootServerMain{
	
	private static String key;
	
	public static void main(String[] args){
		try{
			System.out.println("Connectaddresse: " + InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
			key = getRandomAccessKey(3);
			System.out.println("Accesskey: " + key);
			
			ServerSocket server = new ServerSocket(25566, 0, InetAddress.getByName(InetAddress.getLocalHost().getHostName()));
			
			final int TARGET_TICK_RATE = 30;
			
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