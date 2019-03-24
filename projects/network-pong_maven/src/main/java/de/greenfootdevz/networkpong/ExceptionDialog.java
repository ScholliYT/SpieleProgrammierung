package de.greenfootdevz.networkpong;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExceptionDialog extends JDialog{
	
	private static final long serialVersionUID = -8479553276597292535L;
	private JTextArea textAreaException;
	
	public ExceptionDialog(Throwable error){
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		JPanel content = new JPanel();
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(content);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Upps, ein Fehler ist aufgetreten");
		setSize(550, 381);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTextArea txtrGeneralInformation = new JTextArea();
		txtrGeneralInformation.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtrGeneralInformation.setEditable(false);
		txtrGeneralInformation.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtrGeneralInformation.setText("Während der Ausführung des Spiels ist ein unwerwarteter und schwerwiegender Fehler aufgetreten. Akkurate Fehlermeldung:");
		txtrGeneralInformation.setBackground(SystemColor.menu);
		txtrGeneralInformation.setWrapStyleWord(true);
		txtrGeneralInformation.setLineWrap(true);
		txtrGeneralInformation.setHighlighter(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textAreaException = new JTextArea();
		textAreaException.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textAreaException.setWrapStyleWord(true);
		textAreaException.setLineWrap(true);
		textAreaException.setEditable(false);
		textAreaException.setForeground(new Color(255, 0, 0));
		textAreaException.setHighlighter(null);
		scrollPane.setViewportView(textAreaException);
		
		textAreaException.setText(error.toString() +  "\n");
		
		JButton btnTerminate = new JButton("Beenden");
		btnTerminate.addActionListener(e -> System.exit(0));
		btnTerminate.setFocusPainted(false);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(txtrGeneralInformation);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.add(btnTerminate);
		
		for(StackTraceElement element: error.getStackTrace()){
			textAreaException.append("at " + element.toString() + "\n");
		}
		
	}
}