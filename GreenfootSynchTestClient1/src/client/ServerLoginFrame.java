package client;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

public class ServerLoginFrame extends JFrame{
	
	private static final long serialVersionUID = 9201712848844039556L;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	public ServerLoginFrame() {
		setResizable(false);
		setTitle("Mit einem Gameserver verbinden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 152);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblIp = new JLabel("IP:");
		contentPane.add(lblIp);
		
		textField = new JTextField();
		textField.setText("192.168.178.73");
		contentPane.add(textField);
		textField.setColumns(20);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(25566, 0, 65566, 1));
		contentPane.add(spinner);
		
		JLabel lblSchlssel = new JLabel("Schlüssel:");
		contentPane.add(lblSchlssel);
		
		textField_1 = new JTextField();
		contentPane.add(textField_1);
		textField_1.setColumns(20);
		
		JButton btnVerbinden = new JButton("Verbinden!");
		btnVerbinden.addActionListener(e -> {
			try{
				ConnectionManager mgr = new ConnectionManager(textField.getText(), textField_1.getText());
				mgr.attemptConnect();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		});
		btnVerbinden.setFocusPainted(false);
		contentPane.add(btnVerbinden);
	}
	
}