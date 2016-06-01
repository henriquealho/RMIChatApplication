package pt.ipb.sd;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import pt.ipb.sd.common.IClient;
import pt.ipb.sd.common.IServer;

public class Server implements IServer {

	String name;
	List<IClient> clients = new LinkedList<>();

	Server() throws RemoteException {
		this.name = JOptionPane.showInputDialog("Enter the name for the server: ");
	}

	@Override
	public void send(String msg) throws RemoteException {
		System.out.println(msg);
		for (IClient client : clients) {
				client.send(msg);
		}
	}

	@Override
	public boolean addClient(IClient c) throws RemoteException {
		return clients.add(c);
	}

	@Override
	public boolean remClient(IClient c) throws RemoteException {
		return clients.remove(c);
	}

	public String getName() throws RemoteException {
		return this.name;
	}

	public void setName(String name) throws RemoteException {
		this.name = name;
	}

	public void makeConnection() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// server connection configuration
			Registry registry = LocateRegistry.createRegistry(1099);
			IServer stub = (IServer) UnicastRemoteObject.exportObject(this, 0);
			registry.rebind(this.getName(), stub);

			JOptionPane.showMessageDialog(null, "[" + this.getName() + "] is online and ready for connection!");

		} catch (Exception e) {
			System.out.println("[System] failed: " + e);
		}
	}
}
