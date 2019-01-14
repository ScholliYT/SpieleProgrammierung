import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;

public class ProjectFrame extends JFrame{
	
	private static final long serialVersionUID = -9093795554472227677L;
	private JCheckBoxMenuItem chckbxmntmVerbindungenZulassen;
	private JLabel lblImage;
	
	public ProjectFrame() {
		setTitle("ScreenOverSocket");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 570);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Bildschirm von verbundenem Rechner", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		lblImage = new JLabel("");
		panel_1.add(lblImage, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		chckbxmntmVerbindungenZulassen = new JCheckBoxMenuItem("Verbindung zulassen");
		chckbxmntmVerbindungenZulassen.setSelected(true);
		mnDatei.add(chckbxmntmVerbindungenZulassen);
	}
	
	public void update(ImageIcon icn){
//		lblImage.setIcon(null);
		lblImage.setIcon(new ImageIcon(icn.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_DEFAULT)));
	}
	
	public boolean getAllowConnections(){
		return chckbxmntmVerbindungenZulassen.isSelected();
	}
}