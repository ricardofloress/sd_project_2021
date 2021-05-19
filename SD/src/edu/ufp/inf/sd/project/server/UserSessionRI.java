package edu.ufp.inf.sd.project.server;

import edu.ufp.inf.sd.project.client.ObserverRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface UserSessionRI extends Remote {
    public void logout() throws RemoteException;

    public void print(String msg) throws RemoteException;

    public ClientUser getUser() throws RemoteException;

    public void deleteJobGroup(SubjectRI subjectRI) throws RemoteException;

    public SubjectRI createJobGroup(Task task, UserSessionRI userSessionRI) throws RemoteException;

    public ArrayList<SubjectRI> listJobGroups() throws RemoteException;

    public void pauseJobGroup(SubjectRI subjectRI) throws RemoteException;

    public void resumeJobGroup(SubjectRI subjectRI) throws RemoteException;

    public void addWorkerToJobGroup(SubjectRI subjectRI, ObserverRI observerRI) throws RemoteException;

    public void removeWorkerToJobGroup(SubjectRI subjectRI, ObserverRI observerRI) throws RemoteException;

    public void notifyWorkersOfJobGroup(SubjectRI subjectRI) throws RemoteException;

}
