package edu.ufp.inf.sd.project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserFactoryRI extends Remote {
    public boolean register(String uname, String pw) throws RemoteException;
    public UserSessionRI login(String uname, String pw) throws RemoteException;
}

