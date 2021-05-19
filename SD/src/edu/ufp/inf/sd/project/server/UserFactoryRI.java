package edu.ufp.inf.sd.project.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UserFactoryRI extends Remote {
    public boolean register(String uname, String pw) throws RemoteException;

    public UserSessionRI login(String uname, String pw) throws RemoteException;

    public void createJobGroup(SubjectRI subjectRI) throws RemoteException;

    public ArrayList<SubjectRI> listJobGroups() throws RemoteException;

}

