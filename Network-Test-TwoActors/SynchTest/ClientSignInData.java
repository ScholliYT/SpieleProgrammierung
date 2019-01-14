
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ClientSignInData implements Serializable{
	
	private static final long serialVersionUID = -6598836096708197659L;
	
	private String username, encryptedKey;
	
	public ClientSignInData(String username, String rawKey){
		this.username = username;
		this.encryptedKey = encryptKey(rawKey);
	}
	
	private static String encryptKey(String rawKey){
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.update(rawKey.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			byte[] data = digest.digest();
			for(int i = 0; i< data.length; i++){
				sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getUsername(){
		return username;
	}
	
	public boolean accept(String rawKey){
		String encryptedInput = encryptKey(rawKey);
		return encryptedInput.equals(encryptedKey);
	}
}