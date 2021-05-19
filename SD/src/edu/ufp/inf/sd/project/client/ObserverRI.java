package edu.ufp.inf.sd.project.client;

import edu.ufp.inf.sd.project.server.SubjectRI;
import edu.ufp.inf.sd.project.server.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ObserverRI extends Remote {
    public void update() throws RemoteException;

    public void notifyJobGroup() throws RemoteException;

    public void setState(WorkerState state) throws RemoteException;

    public WorkerState getState() throws RemoteException;

    public Integer getWorkResult() throws RemoteException;

    public Task getCurrentTask() throws RemoteException;

    public void setCurrentTask(Task task) throws RemoteException;

    int runTS() throws RemoteException;

    public void setJobGroup(SubjectRI subjectRI) throws RemoteException;

    public SubjectRI getJobGroup() throws RemoteException;

}
