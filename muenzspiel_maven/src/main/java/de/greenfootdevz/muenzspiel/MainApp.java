package de.greenfootdevz.muenzspiel;

import javax.swing.UIManager;

import greenfoot.export.GreenfootScenarioMain;

public class MainApp extends GreenfootScenarioMain {

	/**
	 * Main method for Character Editor. This gets executed before anything else.
	 * @throws Exception 
	 */
	public static void main(String[] args){
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			GreenfootScenarioMain.main(args);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
