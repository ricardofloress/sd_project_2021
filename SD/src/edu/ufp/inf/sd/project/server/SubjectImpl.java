package edu.ufp.inf.sd.project.server;

import edu.ufp.inf.sd.project.client.ObserverImpl;
import edu.ufp.inf.sd.project.client.ObserverRI;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {

    private State subjectState;
    private Task task;
    private ArrayList<ObserverRI> observers;
    private UserSessionRI userSessionRI;
    private HashMap<ObserverRI, Integer> finalResults;


    public SubjectImpl() throws RemoteException {
        super();
        this.observers = new ArrayList<>();
        this.finalResults = new HashMap<>();
        this.subjectState = new State("", "", State.JobGroupState.CREATED);
    }

    public SubjectImpl(Task task, UserSessionRI userSessionRI) throws RemoteException {
        super();
        this.subjectState = new State("", "", State.JobGroupState.CREATED);
        this.observers = new ArrayList<>();
        this.task = task;
        this.userSessionRI = userSessionRI;
        this.finalResults = new HashMap<>();

    }

    @Override
    public void print(String msg) throws RemoteException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "someone called me with msg = {0}", new Object[]{msg});
    }


    /**
     * @desc attach a worker to this Job Group
     * @param obsRI
     * @throws RemoteException
     */
    @Override
    public void attach(ObserverRI obsRI) throws RemoteException {
//        obsRI.getState().setAvailable(false);
//        obsRI.getState().setMsg("Attached to JobGroup");
        obsRI.changeMyState(obsRI.getUsername() + " attached to JobGroup...");
        this.observers.add(obsRI);
        obsRI.setJobGroup(this);
        System.out.println(obsRI.getState().getMsg()+  " ------ is Available? " + obsRI.getState().getAvailable());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Attached = {0}", obsRI.getUsername());
    }

    /**
     * @desc dettach a worker from this Job Group
     * @param obsRI
     * @throws RemoteException
     */
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
    }

    @Override
    public State getState() throws RemoteException {
        return this.subjectState;
    }

    /**
     * @desc This method is going to notify all workers to update their state and start working, and the job group is going to change his onw state
     * @throws RemoteException
     */
    @Override
    public void notifyAllWorkers() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.DISTRIBUTION);
        for (ObserverRI obs : this.observers) {
            System.out.println(obs.getUsername() + " is going to start working...");
            //Set the current task of the worker
            obs.setCurrentTask(task);
            //Changes the worker state to not available
            obs.update();
        }
    }

    /**
     * @desc Put the result of the worker in his hash map and if all workers are available means that all ended their worker, so the jobgroup is going to choose the winner and give the respective credits
     * @param observerRI
     * @param workerResult
     * @throws RemoteException
     */
    @Override
    public void update(ObserverRI observerRI, Integer workerResult) throws RemoteException {
        System.out.println(observerRI.getState().getMsg() + " ------ isAvailable: " + observerRI.getState().getAvailable());
        System.out.println("The worker " + observerRI.getUsername() + " returned to she server the value: " + workerResult);
        this.finalResults.put(observerRI, workerResult);

        //Check if all workers are done
        Integer count = 0;

        System.out.println("At this moment when the " + observerRI.getUsername() + " is giving feedback to the server...");
        for (ObserverRI observerCheck : this.observers) {
            System.out.println("The worker " + observerCheck.getUsername() + " is Available? " + observerCheck.getState().getAvailable());
            if (observerCheck.getState().getAvailable()) {
                count++;
            }
        }

        System.out.println("There are " + count + " workers finished...");

        //if they are done we are going to choose the winner
        if (count == this.observers.size()) {
            ObserverRI obsWinner = null;
            Integer biggestResult = Integer.MAX_VALUE;
            if (!this.finalResults.isEmpty()) {
                for (ObserverRI obs : this.finalResults.keySet()) {
                    Integer result = this.finalResults.get(obs);
                    if (result < biggestResult) {
                        biggestResult = result;
                        obsWinner = obs;
                    }
                }
                obsWinner.addCredits(10);
                for (ObserverRI obs : this.observers) {
                    if (!obs.equals(obsWinner))
                        obs.addCredits(1);
                }
                //Then we have to remove the credits from the onwer of the JobGroup
                this.userSessionRI.removeCreditsFromOnwer((this.observers.size() - 1) + 10);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "The fastest worker was {0}", obsWinner.getUsername());
            }
        }
    }


    /**
     * @desc pauses the jobgroup
     * @throws RemoteException
     */
    @Override
    public void pause() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.STOPED);
    }

    /**
     * @desc stop the jobgroup
     * @throws RemoteException
     */
    @Override
    public void stop() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.RECIEVED);

    }

    /**
     * @desc resume the jobgroup
     * @throws RemoteException
     */
    @Override
    public void resume() throws RemoteException {
        this.subjectState.setState(State.JobGroupState.DISTRIBUTION);
    }

    /**
     * @desc delete the jobgroup
     * @throws RemoteException
     */
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

    public HashMap<ObserverRI, Integer> getFinalResults() {
        return finalResults;
    }

    public void setFinalResults(HashMap<ObserverRI, Integer> finalResults) {
        this.finalResults = finalResults;
    }
}

