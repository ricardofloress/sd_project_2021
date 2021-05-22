package edu.ufp.inf.sd.project.server;

import edu.ufp.inf.sd.project.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class UserSessionImpl extends UnicastRemoteObject implements UserSessionRI {

    UserFactoryRI userFactoryRI;

    ClientUser user;

    public UserSessionImpl(UserFactoryRI userFactoryRI, ClientUser user) throws RemoteException {
        super();
        this.userFactoryRI = userFactoryRI;
        this.user = user;
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

    @Override
    public ClientUser getUser() throws RemoteException {
        return this.user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }

    @Override
    public void deleteJobGroup(SubjectRI subjectRI) throws RemoteException {
        UserFactoryImpl userFactory = ((UserFactoryImpl) userFactoryRI);
        userFactory.getDb().removeJobGroup(subjectRI);
    }

    @Override
    public SubjectRI createJobGroup(Task task) throws RemoteException {
        SubjectImpl subjectRI = new SubjectImpl(task, this);
        this.userFactoryRI.createJobGroup(subjectRI);
        return subjectRI;
    }

    @Override
    public ArrayList<SubjectRI> listJobGroups() throws RemoteException {
        return this.userFactoryRI.listJobGroups();
    }

    @Override
    public void pauseJobGroup(SubjectRI subjectRI) throws RemoteException {
        subjectRI.pause();
    }

    @Override
    public void resumeJobGroup(SubjectRI subjectRI) throws RemoteException {
        subjectRI.resume();
    }

    @Override
    public void addWorkerToJobGroup(SubjectRI subjectRI, ObserverRI observerRI) throws RemoteException {
        subjectRI.attach(observerRI);
    }

    @Override
    public void removeWorkerToJobGroup(SubjectRI subjectRI, ObserverRI observerRI) throws RemoteException {
        subjectRI.detach(observerRI);
    }

    @Override
    public void notifyWorkersOfJobGroup(SubjectRI subjectRI) throws RemoteException {
        subjectRI.notifyAllWorkers();
    }
}
