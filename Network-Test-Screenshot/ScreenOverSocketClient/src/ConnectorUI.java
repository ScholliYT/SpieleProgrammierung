import javax.swing.*;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ConnectorUI extends JFrame{



	private static final long serialVersionUID = 2163651533817043696L;
	private JTextField textField;
	private JLabel lblVerbunden;
	
	public ConnectorUI(int port){
		setResizable(false);
		setTitle("Verbinden");
		setBounds(100, 100, 400, 90);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblVerbindenZu = new JLabel("Verbinden zu:");
		lblVerbindenZu.setBounds(5, 11, 95, 14);
		getContentPane().add(lblVerbindenZu);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("192.168.178.23");
		textField.setBounds(105, 8, 195, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnVerbinden = new JButton("Verbinden!");
		btnVerbinden.setBounds(300, 7, 95, 23);
		btnVerbinden.addActionListener(e -> {
			Thread connection = new Thread(() -> {
				try{
					Socket s = new Socket(InetAddress.getByName(textField.getText()), port);
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
						oos.writeObject(byteOut.toByteArray());
						oos.flush();
						oos.reset();
						long sent = System.currentTimeMillis();
						System.out.println("Taking pic=" + (imgTime - start) + "ms; size=" + byteOut.size() + " bytes; send=" + (sent - imgTime) + "ms");
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this, getExceptionText(ex), ex.getClass().getName(), JOptionPane.ERROR_MESSAGE);
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

	private String getExceptionText(Exception ex) {
		StringBuilder sb = new StringBuilder();
		sb.append(ex.toString());
		for (StackTraceElement ste : ex.getStackTrace()) {
			sb.append("    ");
			sb.append(ste);
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}