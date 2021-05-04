package edu.ufp.inf.sd.rmi._06_visitor.server;

public class ConcreteElementeFolderMagazinesImpl {
    private SingletonFolderOperationsMagazines stateMagazineFolder;

    public SingletonFolderOperationsMagazines getStateMagazineFolder() {
        return stateMagazineFolder;
    }

    public void setStateMagazineFolder(SingletonFolderOperationsMagazines stateMagazineFolder) {
        this.stateMagazineFolder = stateMagazineFolder;
    }
}
