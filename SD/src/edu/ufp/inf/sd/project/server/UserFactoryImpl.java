package edu.ufp.inf.sd.project.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class UserFactoryImpl extends UnicastRemoteObject implements UserFactoryRI {

    private DBMockup db;

    protected UserFactoryImpl() throws RemoteException {
        super();
        db = new DBMockup();
    }

    @Override
    public boolean register(String uname, String pw) throws RemoteException {
        if (!db.exists(uname, pw)) {
            System.out.println(uname + " " + pw);
            db.register(uname, pw);
            return true;
        }
        return false;
    }

    @Override
    public UserSessionRI login(String uname, String pw) throws RemoteException {
        if (db.exists(uname, pw)) {
            if (!this.db.getSessions().containsKey(uname)) {
                for (ClientUser user : db.getClientUsers()) {
                    if (user.getUname().equals(uname)) {
                        UserSessionRI userSessionRI = new UserSessionImpl(this, new ClientUser(uname, pw));
                        this.db.addSession(uname, userSessionRI);     // insere no hashmap de sessoes
                        return userSessionRI;
                    }
                }
            } else {
                return this.db.getSessions().get(uname);
            }
        }
        return null;
    }

    @Override
    public void createJobGroup(SubjectRI subjectRI) throws RemoteException {
        this.db.addJobGroup(subjectRI);
    }

    @Override
    public ArrayList<SubjectRI> listJobGroups() throws RemoteException {
        return this.db.getSubjectRIS();
    }

    public DBMockup getDb() {
        return db;
    }
}
