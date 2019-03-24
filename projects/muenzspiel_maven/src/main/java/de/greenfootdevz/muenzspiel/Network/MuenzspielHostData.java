package de.greenfootdevz.muenzspiel.Network;

import java.util.List;

import de.greenfootdevz.muenzspiel.Actor.Coin;

import java.io.Serializable;

/**
 * Diese Klasse stellt einen Datenpaket dar, welches vom Host zum Client
 * gesendet wird.
 */
public class MuenzspielHostData implements Serializable{
	
	private static final long serialVersionUID = 4498114982435349255L;
	
	
	
	public MuenzspielHostData(List<Coin> coins){
		
	}
	
}