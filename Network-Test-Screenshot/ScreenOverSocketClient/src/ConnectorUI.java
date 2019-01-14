import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ConnectorUI extends JFrame{
	
	private static final long serialVersionUID = 2163651533817043696L;
	private JTextField textField;
	private JLabel lblVerbunden;
	
	public ConnectorUI(){
		setResizable(false);
		setTitle("Verbinden");
		setBounds(100, 100, 443, 90);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblVerbindenZu = new JLabel("Verbinden zu:");
		lblVerbindenZu.setBounds(10, 11, 66, 14);
		getContentPane().add(lblVerbindenZu);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("192.168.178.23");
		textField.setBounds(86, 8, 246, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnVerbinden = new JButton("Verbinden!");
		btnVerbinden.setBounds(342, 7, 85, 23);
		btnVerbinden.addActionListener(e -> {
			Thread connection = new Thread(() -> {
				try{
					Socket s = new Socket(InetAddress.getByName(textField.getText()), 25566);
					s.setTcpNoDelay(true);
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					lblVerbunden.setVisible(true);
					Robot r = new Robot();
					while(true){
						long start = System.currentTimeMillis();
						BufferedImage img = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
						ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
						ImageIO.write(img, "jpg", byteOut);
						long imgTime = System.currentTimeMillis();
						long create = System.currentTimeMillis();
						oos.writeObject(byteOut.toByteArray());
						oos.flush();
						oos.reset();
						long sent = System.currentTimeMillis();
						System.out.println("Taking pic=" + (imgTime - start) + "ms; " + "create=" + (create - imgTime) + "ms; send=" + (sent - create) + "ms");
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			});
			connection.start();
		});
		getContentPane().add(btnVerbinden);
		
		lblVerbunden = new JLabel("Verbunden!");
		lblVerbunden.setVisible(false);
		lblVerbunden.setBounds(86, 36, 56, 14);
		getContentPane().add(lblVerbunden);
	}
}