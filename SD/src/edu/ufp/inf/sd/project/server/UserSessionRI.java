package edu.ufp.inf.sd.project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserSessionRI extends Remote {
    public void logout() throws RemoteException;

    void print(String msg) throws RemoteException;

}
