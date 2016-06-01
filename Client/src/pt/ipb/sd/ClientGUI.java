package pt.ipb.sd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import pt.ipb.sd.common.IClient;
import pt.ipb.sd.common.IServer;

public class ClientGUI extends Client {

	ClientGUI mainGUI;
	JFrame newFrame = new JFrame("ChatApplication v1.2.0 (" + this.getName() + ")");
	JButton sendMessage;
	JTextField messageBox;
	JTextArea chatBox = new JTextArea();
	JFrame preFrame;
	IClient client = (IClient) this;
	String username = this.getName();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClientGUI mainGUI = new ClientGUI();
		mainGUI.display();
	}

	public void makeConnection() {
		// connection to the server
		try {
			Registry registry = LocateRegistry.getRegistry(this.getServerAddress());
			server = (IServer) registry.lookup(this.getServerName());
			this.setServer(server);

			IClient stub = (IClient) UnicastRemoteObject.exportObject((IClient) this, 0);
			server.addClient((IClient) stub);
			server.send("[" + username + "] is connected.\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void display() {

		newFrame.setVisible(true);
		JPanel southPanel = new JPanel();
		newFrame.add(BorderLayout.SOUTH, southPanel);
		southPanel.setBackground(Color.BLUE);
		southPanel.setLayout(new GridBagLayout());

		messageBox = new JTextField(30);
		sendMessage = new JButton("Send Message");
		chatBox.setEditable(false);
		newFrame.add(new JScrollPane(chatBox), BorderLayout.CENTER);

		chatBox.setLineWrap(true);

		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.WEST;
		GridBagConstraints right = new GridBagConstraints();
		right.anchor = GridBagConstraints.EAST;
		right.weightx = 2.0;

		southPanel.add(messageBox, left);
		southPanel.add(sendMessage, right);

		chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
		sendMessage.addActionListener(new sendMessageButtonListener());
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setSize(470, 300);
		newFrame.addWindowListener(new WindowHandler());
		makeConnection();
	}

	class sendMessageButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (messageBox.getText().length() < 1) {
				// do nothing
			} else if (messageBox.getText().equals(".clear")) {
				chatBox.setText("Cleared all messages\n");
				messageBox.setText("");
			} else {
				try {
					server.send("[" + username + "]:  " + messageBox.getText() + "\n");
				} catch (RemoteException e) {
					System.out.println("[System] failed: " + e);
				}
			}
		}
	}

	class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			newFrame.dispose();
			try {
				server.send("[" + username + "] " + "disconnected.\n");
				server.remClient(client);
			} catch (RemoteException e1) {
				System.out.println("[System] failed: " + e1);
			}
			System.exit(0);
		}
	}

	@Override
	public void send(String msg) throws RemoteException {
		chatBox.append(msg);
		messageBox.setText("");
	}
}
