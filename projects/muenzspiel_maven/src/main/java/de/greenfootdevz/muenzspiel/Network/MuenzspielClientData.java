package de.greenfootdevz.muenzspiel.Network;

import java.io.Serializable;

import de.greenfootdevz.muenzspiel.Actor.SerialCoin;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Client zum Host
 * gesendet wird.
 */
public class MuenzspielClientData implements Serializable{
	
	private static final long serialVersionUID = -6243741163353349874L;
	
	private SerialCoin[] coins;
	
	public MuenzspielClientData(SerialCoin[] coins){
		this.coins = coins;
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