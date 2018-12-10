

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SynchronizedTickData implements Serializable{
	
	private static final long serialVersionUID = 759605546953382276L;
	
	private ArrayList<ActorState> allActors;
	
	public SynchronizedTickData(){
		this.allActors = new ArrayList<>();
	}
	
	public void addClient(ActorState[] data){
		allActors.addAll(Arrays.asList(data));
	}
	
	public ArrayList<ActorState> getAllActors(){
		return allActors;
	}
	
}