package edu.ufp.inf.sd.project.client;

import edu.ufp.inf.sd.project.server.SubjectRI;
import edu.ufp.inf.sd.project.server.Task;
import edu.ufp.inf.sd.project.util.tabusearch.TabuSearchJSSP;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {
    public String username;

    public WorkerState lastObserverState;

    public SubjectRI subjectRI;

    private Integer credits;

    private Integer workResult;

    private Task currentTask;


    public ObserverImpl(String username, Integer credits) throws RemoteException {
        super();
        this.username = username;
        this.credits = credits;
    }


    @Override
    public void update() throws RemoteException {
        this.lastObserverState = new WorkerState("Start Working...", this.username, false);
    }

    @Override
    public void notifyJobGroup() throws RemoteException {
        this.lastObserverState.setMsg("Finished...");
        this.lastObserverState.setAvailable(true);
        this.subjectRI.update(this);
    }

    @Override
    public void setState(WorkerState state) throws RemoteException {
        this.lastObserverState = state;
    }

    @Override
    public WorkerState getState() throws RemoteException {
        return this.lastObserverState;
    }

    @Override
    public Integer getWorkResult() throws RemoteException {
        return this.workResult;
    }

    @Override
    public Task getCurrentTask() throws RemoteException {
        return this.currentTask;
    }

    @Override
    public void setCurrentTask(Task task) throws RemoteException {
        this.currentTask = task;
    }

    @Override
    public int runTS() throws RemoteException {

        TabuSearchJSSP ts = new TabuSearchJSSP(this.currentTask.getPath());
        int makespan = ts.run();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "[TS] Makespan for {0} = {1}", new Object[]{this.currentTask.getPath(), String.valueOf(makespan)});
        this.workResult = makespan;
        notifyJobGroup();
        return makespan;
    }

    @Override
    public void setJobGroup(SubjectRI subjectRI) throws RemoteException {
        this.subjectRI = subjectRI;
    }

    @Override
    public SubjectRI getJobGroup() throws RemoteException {
        return this.subjectRI;

    }

    @Override
    public String getUsername() {
        return username;
    }

    public SubjectRI getSubjectRI() {
        return subjectRI;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}

