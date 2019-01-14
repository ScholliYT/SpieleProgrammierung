

import java.io.Serializable;

public class ClientSynchData implements Serializable{
	
	private static final long serialVersionUID = 6818998321461678045L;
	
	private ActorState[] myActors;
	
	public ClientSynchData(ActorState... actorStates){
		this.myActors = actorStates;
	}
	
	public ActorState[] getMyActors(){
		return myActors;
	}
	
}