package services;

import model.Game;
import model.User;

import java.util.List;

public interface IService {
    User login(IObserver client, String username, String password);
    void logout(IObserver client, String username);
    void updateGame(String winner);
    void twoPlayers();
    void actualGameStarted();
    void positionChoosed(String user, Integer position);
    void shoot(String user, Integer position);
}
