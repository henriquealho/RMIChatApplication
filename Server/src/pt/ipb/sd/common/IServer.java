package pt.ipb.sd.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

	public void send(String msg) throws RemoteException;

	public boolean addClient(IClient c) throws RemoteException;

	public boolean remClient(IClient c) throws RemoteException;
}
