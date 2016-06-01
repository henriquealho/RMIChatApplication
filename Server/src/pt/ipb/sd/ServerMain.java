package pt.ipb.sd;

import java.rmi.RemoteException;

public class ServerMain {
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.makeConnection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}