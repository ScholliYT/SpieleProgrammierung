import javax.swing.UIManager;

public class ClientMain{

    private static final int PORT = 25560;

	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


			ConnectorUI ui = new ConnectorUI(PORT);
			ui.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}