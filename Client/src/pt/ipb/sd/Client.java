package pt.ipb.sd;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import pt.ipb.sd.common.IClient;
import pt.ipb.sd.common.IServer;

public class Client implements IClient {

	String name;
	IServer server;
	String serverName;
	String serverAddress;

	public Client() {
		// data input
		this.name = JOptionPane.showInputDialog("Enter your name: ");
		this.serverAddress = JOptionPane.showInputDialog("Enter the server address: ");
		this.serverName = JOptionPane.showInputDialog("Enter the server name: ");
	}

	@Override
	public void send(String msg) throws RemoteException {
		System.out.println(msg);
	}

	public void setName(String userName) {
		this.name = userName;
	}

	public String getName() {
		return this.name;
	}

	public void setServer(IServer server) {
		this.server = server;
	}

	public IServer getServer() {
		return this.server;
	}

	public void setServerAddress(String address) {
		this.serverAddress = address;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}
}
