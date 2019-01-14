import javax.swing.UIManager;

public class ClientMain{
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			ConnectorUI ui = new ConnectorUI();
			ui.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}