package edu.ufp.inf.sd.rmi._02_calculator.server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CalculatorImpl extends UnicastRemoteObject implements CalculatorRI {


    public CalculatorImpl() throws RemoteException {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException {
        double soma = a+ b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "SOMA = {0)",soma);
        return 0;
    }

    @Override
    public double add(ArrayList<Double> list) throws RemoteException {
        return 0;
    }

    @Override
    public double div(double a, double b) throws RemoteException {
        double div = 0;
        if(b==0){
            throw new RemoteArithmeticException("ERROO");
        }
        else div = a/b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "div = {0)",div);

        return 0;
    }

    public double mult(double a, double b) throws RemoteException {
        double multi = a*b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Multiplication = {0)",multi);
        return multi;
    }

    public double sub(double a, double b) throws RemoteException {
        double subtract = a-b;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Subtract = {0}",subtract);
        return subtract;
    }



}
