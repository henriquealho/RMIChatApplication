package pt.ipb.sd.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {

	public void send(String msg) throws RemoteException;
}