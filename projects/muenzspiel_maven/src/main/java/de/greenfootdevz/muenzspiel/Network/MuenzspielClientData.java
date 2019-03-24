package de.greenfootdevz.muenzspiel.Network;

import java.io.Serializable;

import de.greenfootdevz.muenzspiel.Actor.Coin;
import de.greenfootdevz.muenzspiel.Actor.SerialCoin;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Client zum Host
 * gesendet wird.
 */
public class MuenzspielClientData implements Serializable{
	
	private static final long serialVersionUID = -6243741163353349874L;
	
	private SerialCoin[] sCoins;
	
	public MuenzspielClientData(Coin[] coins){
		sCoins = new SerialCoin[coins.length];
		
		for(int i = 0; i < sCoins.length; i++){
			sCoins[i] = new SerialCoin(coins[i]);
		}
	}
	
	public SerialCoin[] getCoins(){
		return sCoins;
	}
	
	@Override
	public String toString(){
		String result = "{";
		
		for(SerialCoin c: sCoins){
			result += c.toString();
		}
		return result + "}";
	}
	
}