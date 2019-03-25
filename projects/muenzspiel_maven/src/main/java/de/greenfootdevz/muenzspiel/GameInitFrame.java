package de.greenfootdevz.muenzspiel;

import de.greenfootdevz.muenzspiel.Network.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

public class GameInitFrame extends JFrame {

	private static final long serialVersionUID = -3194927350783500407L;

	private JPanel panelClient;
	private JRadioButton rdbtnHost;
	private JRadioButton rdbtnClient;
	private JComboBox<String> cbIpClient;
	private JSpinner spinnerPortClient;
	private JButton btnNetworkScan;
	private JButton btnPlay;
	private JPanel panelHostoptions;
	private JComboBox<String> cbNetworkInterfaces;
	private JSpinner spinnerPortHost;

	private boolean isHost;

	private InetAddress selectedAddress;
	private int selectedPort;
	private NetworkingDevices networking;

	private NetworkConfig networkConfig;

	public GameInitFrame() throws Exception {
		this.networkConfig = NetworkConfigManager.getConfig("networkconfig.xml");

		System.out.println("Using NetworkConfig: \n" + networkConfig.toString());

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				exit();
			}
		});
		setResizable(false);
		setTitle("Muenzspiel - Starteinstellungen");
		setBounds(100, 100, 450, 293);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		ButtonGroup group = new ButtonGroup();

		panelClient = new JPanel();
		panelClient.setBorder(
				new TitledBorder(null, "Einem Spiel beitreten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		getContentPane().add(panelClient, BorderLayout.CENTER);
		panelClient.setLayout(null);

		JLabel lblHostaddress = new JLabel("Hostadresse:");
		lblHostaddress.setBounds(10, 21, 64, 14);
		panelClient.add(lblHostaddress);

		cbIpClient = new JComboBox<>();
		cbIpClient.setEditable(true);
		cbIpClient.setBounds(84, 18, 231, 20);
		panelClient.add(cbIpClient);

		btnNetworkScan = new JButton("Netzwerkscan");
		btnNetworkScan.setFocusPainted(false);
		btnNetworkScan.setBounds(325, 17, 99, 23);
		btnNetworkScan.addActionListener(action -> {
			Thread worker = new Thread(() -> {
				ArrayList<String> results = new ArrayList<>();
				try {
					DatagramSocket socket = new DatagramSocket();
					socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
					InetAddress local = socket.getLocalAddress();
					socket.close();

					byte[] localByte = local.getAddress();

					for (int i = 0; i <= 255; i++) {
						InetAddress inet = InetAddress
								.getByAddress(new byte[] { localByte[0], localByte[1], localByte[2], (byte) i });
						Thread ipChecker = new Thread(() -> {
							try {
								if (inet.isReachable(1000)) {
									synchronized (results) {
										results.add(inet.getHostAddress());
									}
								}
							} catch (Exception ignore) {
							}
						});
						ipChecker.start();
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {
					Thread.sleep(1500);
				} catch (InterruptedException ignore) {
				}
				((DefaultComboBoxModel<String>) cbIpClient.getModel()).removeAllElements();
				Collections.sort(results);
				for (String s : results) {
					cbIpClient.addItem(s);
				}
			});
			worker.start();
		});
		panelClient.add(btnNetworkScan);

		JLabel lblPortClient = new JLabel("Port:");
		lblPortClient.setBounds(10, 46, 46, 14);
		panelClient.add(lblPortClient);

		spinnerPortClient = new JSpinner();
		spinnerPortClient.setModel(new SpinnerNumberModel(networkConfig.getPort(), 0, 65535, 1));
		spinnerPortClient.setBounds(84, 43, 58, 20);
		spinnerPortClient.addChangeListener(change -> this.selectedPort = (int) spinnerPortClient.getValue());
		panelClient.add(spinnerPortClient);

		JPanel contentPane = new JPanel();
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelOperationMode = new JPanel();
		contentPane.add(panelOperationMode, BorderLayout.NORTH);
		FlowLayout fl_panelOperationMode = (FlowLayout) panelOperationMode.getLayout();
		fl_panelOperationMode.setAlignment(FlowLayout.LEFT);
		panelOperationMode.setBorder(new TitledBorder(null, "M\u00F6chten Sie ein neues Spiel hosten oder beitreten?",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		rdbtnHost = new JRadioButton("Ein Spiel hosten");
		rdbtnHost.setFocusPainted(false);
		rdbtnHost.setSelected(true);
		rdbtnHost.addActionListener(action -> {
			panelClient.setVisible(false);
			panelHostoptions.setVisible(true);
			contentPane.remove(panelClient);
			contentPane.add(panelHostoptions, BorderLayout.CENTER);
			contentPane.revalidate();
			isHost = true;
		});
		panelOperationMode.add(rdbtnHost);

		rdbtnClient = new JRadioButton("Einem Spiel beitreten");
		rdbtnClient.setFocusPainted(false);
		rdbtnClient.addActionListener(action -> {
			panelClient.setVisible(true);
			panelHostoptions.setVisible(false);
			contentPane.remove(panelHostoptions);
			contentPane.add(panelClient, BorderLayout.CENTER);
			contentPane.revalidate();
			isHost = false;
		});
		panelOperationMode.add(rdbtnClient);
		group.add(rdbtnHost);
		group.add(rdbtnClient);

		panelHostoptions = new JPanel();
		contentPane.add(panelHostoptions, BorderLayout.CENTER);
		panelHostoptions
				.setBorder(new TitledBorder(null, "Hostoptionen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelHostoptions.setLayout(null);

		JLabel lblNetzwerkinterface = new JLabel("Netzwerkinterface");
		lblNetzwerkinterface.setBounds(10, 21, 422, 14);
		panelHostoptions.add(lblNetzwerkinterface);

		cbNetworkInterfaces = new JComboBox<>();
		cbNetworkInterfaces.setBounds(10, 47, 422, 20);
		panelHostoptions.add(cbNetworkInterfaces);

		JLabel lblPortHost = new JLabel("Port");
		lblPortHost.setBounds(10, 79, 422, 14);
		panelHostoptions.add(lblPortHost);

		spinnerPortHost = new JSpinner();
		spinnerPortHost.setModel(new SpinnerNumberModel(networkConfig.getPort(), 0, 65535, 1));
		spinnerPortHost.setBounds(10, 105, 422, 20);
		spinnerPortHost.addChangeListener(change -> this.selectedPort = (int) spinnerPortHost.getValue());
		panelHostoptions.add(spinnerPortHost);

		JPanel panelPlayButton = new JPanel();
		contentPane.add(panelPlayButton, BorderLayout.SOUTH);
		FlowLayout fl_panelPlayButton = (FlowLayout) panelPlayButton.getLayout();
		fl_panelPlayButton.setAlignment(FlowLayout.RIGHT);

		btnPlay = new JButton("Spielen");
		btnPlay.setFocusPainted(false);
		btnPlay.addActionListener(action -> this.setVisible(false));
		panelPlayButton.add(btnPlay);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		// TODO: Beenden Funktionalität implementieren - anstatt nen Issue zu öffnen
		// hätte man es eben implementieren können, uff
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mntmBeenden.addActionListener(action -> exit());
		mnDatei.add(mntmBeenden);

		JPanel panel = new JPanel();
		menuBar.add(panel);

		this.networking = new NetworkingDevices();

		this.selectedAddress = InetAddress.getLocalHost();
		this.selectedPort = networkConfig.getPort();
		this.isHost = true;

		for (String entry : networking.getAvailableInterfaces()) {
			cbNetworkInterfaces.addItem(entry);
		}

		SwingUtilities.updateComponentTreeUI(this);

	}

	private void exit() {
		// Greenfoot.stop();
		// dispose();
		System.exit(0);
	}

	public boolean isHost() throws Exception {
		while (isVisible()) {
			Thread.sleep(10);
		}

		if (isHost) {
			this.selectedAddress = networking
					.getIPv4AddressFor(networking.getInterface(cbNetworkInterfaces.getSelectedIndex() + 1));
		} else {
			this.selectedAddress = InetAddress.getByName((String) cbIpClient.getSelectedItem());
		}
		return isHost;
	}

	public InetAddress getSelectedAddress() {
		return selectedAddress;
	}

	public int getSelectedPort() {
		return selectedPort;
	}
}