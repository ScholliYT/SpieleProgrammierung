
import greenfoot.GreenfootImage;

public class ExtendedGreenfootImage extends GreenfootImage{
	
	private String filename;
	
	public ExtendedGreenfootImage(String filename){
		super(filename);
		this.filename = filename;
	}
	
	public String getFilename(){
		return filename;
	}
	
}