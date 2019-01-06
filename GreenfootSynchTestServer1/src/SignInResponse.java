

import java.io.Serializable;

public class SignInResponse implements Serializable{
	
	private static final long serialVersionUID = 6272615735197021500L;
	
	public static final int RESULT_ACCEPT = 0, RESULT_WRONG_KEY = 1, RESULT_USERNAME_TAKEN = 2, RESULT_ERROR_UNDEFINDED = -1;
	
	private final int response;
	private final int assignedPlayerId;
	private final int idRangeLow, idRangeHigh;
	private final boolean master = false;
	
	public SignInResponse(int responseCode, int playerId, int idRangeLow, int idRangeHigh){
		this.response = responseCode;
		this.assignedPlayerId = playerId;
		this.idRangeHigh = idRangeHigh;
		this.idRangeLow = idRangeLow;
	}
	
	public int getResponseCode(){
		return response;
	}
	
	public int getPlayerId(){
		return assignedPlayerId;
	}
	
	public int getIdRangeLow(){
		return idRangeLow;
	}
	
	public int getIdRangeHigh(){
		return idRangeHigh;
	}
	
	public boolean isMaster(){
		return master;
	}
	
}