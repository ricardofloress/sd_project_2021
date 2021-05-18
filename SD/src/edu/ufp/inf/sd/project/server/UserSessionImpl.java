package edu.ufp.inf.sd.project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserSessionImpl extends UnicastRemoteObject implements UserSessionRI {

    UserFactoryRI userFactoryRI;

    public UserSessionImpl(UserFactoryRI userFactoryRI) throws RemoteException {
        super();
        this.userFactoryRI = userFactoryRI;
    }

    @Override
    public void logout() throws RemoteException {
        UserFactoryImpl userFactory = ((UserFactoryImpl) userFactoryRI);
        userFactory.getDb().removeSession(this);
    }

    @Override
    public void print(String msg) throws RemoteException {
        System.out.println(msg);
    }
}
