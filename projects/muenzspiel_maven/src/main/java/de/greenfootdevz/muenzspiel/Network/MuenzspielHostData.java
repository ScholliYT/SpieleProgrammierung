package de.greenfootdevz.muenzspiel.Network;

import java.util.List;

import de.greenfootdevz.muenzspiel.Actor.Coin;
import de.greenfootdevz.muenzspiel.Actor.SerialCoin;

import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Host zum Client
 * gesendet wird.
 */
public class MuenzspielHostData implements Serializable{
	
	private static final long serialVersionUID = 4498114982435349255L;
	
	private boolean isInitial;
	
	private SerialCoin[] coins;
	
	public MuenzspielHostData(List<Coin> allCoins){
		this(allCoins, false);
	}
	
	public MuenzspielHostData(List<Coin> allCoins, boolean initial){
		this.isInitial = initial;
		coins = new SerialCoin[allCoins.size()];
		for(int i = 0; i < coins.length; i++){
			coins[i] = new SerialCoin(allCoins.get(i));
		}
	}
	
	public boolean isInitial(){
		return isInitial;
	}
	
	public SerialCoin[] getCoins(){
		return coins;
	}
	
	@Override
	public String toString(){
		String result = "{";
		
		for(SerialCoin c: coins){
			result += c.toString();
		}
		return result + "}";
	}
	
}