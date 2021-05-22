package edu.ufp.inf.sd.project.server;

import edu.ufp.inf.sd.project.client.ObserverImpl;
import edu.ufp.inf.sd.project.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {

    private State subjectState;
    private Task task;
    private ArrayList<ObserverRI> observers;
    private UserSessionRI userSessionRI;
    //Fazer uma hash para resultados e observers

    public SubjectImpl() throws RemoteException {
        super();
        this.observers = new ArrayList<>();
        this.subjectState = new State("", "", State.JobGroupState.CREATED);
    }

    public SubjectImpl(Task task, UserSessionRI userSessionRI) throws RemoteException {
        super();
        this.subjectState = new State("", "", State.JobGroupState.CREATED);
        this.observers = new ArrayList<>();
        this.task = task;
        this.userSessionRI = userSessionRI;
    }

    @Override
    public void print(String msg) throws RemoteException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "someone called me with msg = {0}", new Object[]{msg});
    }

    @Override
    public void attach(ObserverRI obsRI) throws RemoteException {
        this.observers.add(obsRI);
        obsRI.setJobGroup(this);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Attached = {0}", obsRI.getUsername());
    }

    @Override
    public void detach(ObserverRI obsRI) throws RemoteException {
        for (ObserverRI obs : this.observers) {
            if (obs.equals(obsRI)) {
                this.observers.remove(this.observers.indexOf(obs));
                obsRI.setJobGroup(null);
            }
        }
    }

    @Override
    public void setState(State state) throws RemoteException {
        this.subjectState = state;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Message = {0}", new Object[]{this.subjectState.getInfo()});
        notifyAllWorkers();
    }

    @Override
    public State getState() throws RemoteException {
        return this.subjectState;
    }

    @Override
    public void notifyAllWorkers() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.DISTRIBUTION);
        for (ObserverRI obs : this.observers) {
            obs.update();
            obs.setCurrentTask(task);
            obs.runTS();
        }
    }

    @Override
    public void update(ObserverRI observerRI) throws RemoteException {
        Stream<ObserverRI> observerRIStream = observers.stream().filter(observerRI1 -> {
            try {
                return observerRI1.getState().getAvailable().equals(true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        });

        if (observerRIStream.count() == this.observers.size()) {
            //escolher o melhor resultado
        }
    }

    @Override
    public void pause() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.STOPED);
    }

    @Override
    public void stop() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.RECIEVED);

    }

    @Override
    public void resume() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.DISTRIBUTION);
    }

    @Override
    public void delete() throws RemoteException {
        this.userSessionRI.deleteJobGroup(this);
    }

    @Override
    public Task getTask() {
        return task;
    }

    public void setTasks(Task task) {
        this.task = task;
    }

    public ArrayList<ObserverRI> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<ObserverRI> observers) {
        this.observers = observers;
    }

    public UserSessionRI getUserSessionRI() {
        return userSessionRI;
    }

    public void setUserSessionRI(UserSessionRI userSessionRI) {
        this.userSessionRI = userSessionRI;
    }
}

