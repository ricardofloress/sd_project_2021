package edu.ufp.inf.sd.rmi._06_visitor.server;

public interface VisitorFoldersOperationsI {
    Object visitConcreteElementsStateBooks(ElementFolderRI element);
    Object visitConcreteElementsStateMagazines(ElementFolderRI element);
}
