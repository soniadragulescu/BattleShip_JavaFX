package services;

import model.Game;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void gameFinished(String winner) throws RemoteException;
    void thereAreTwoPlayers(List<String> players) throws  RemoteException;
    void actualGameStarted() throws RemoteException;
}
