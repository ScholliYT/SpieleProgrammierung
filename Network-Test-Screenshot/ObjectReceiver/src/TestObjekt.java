import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class TestObjekt implements Serializable{
	
	private static final long serialVersionUID = -7694465181777955809L;
	
	private int[] data1;
	private String[] data2;
	
	public TestObjekt(){
		Random rnd = new Random();
		data1 = new int[rnd.nextInt(50)];
		data2 = new String[rnd.nextInt(50)];
		
		for(int i = 0; i < data1.length; i++) {
			data1[i] = rnd.nextInt();
		}
		
		for(int j = 0; j < data2.length; j++) {
			data2[j] = getRandomString(rnd);
		}
		
	}
	
	private String getRandomString(Random rnd){
		char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		
		char[] str = new char[rnd.nextInt(100)];
		
		for(int i = 0; i < str.length; i++){
			str[i] = chars[rnd.nextInt(chars.length)];
		}
		return String.valueOf(str);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data1) + ";" + Arrays.toString(data2);
	}
	
}