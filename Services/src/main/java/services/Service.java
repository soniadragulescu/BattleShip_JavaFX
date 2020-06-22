package services;

import model.Game;
import model.User;
import repos.GameRepo;
import repos.UserRepo;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    private UserRepo userRepo;
    private GameRepo gameRepo;
    private List<IObserver> observers;
    private static Integer participants;
    private static HashMap<String,Integer> positions;
    List<String> users;
    private boolean gameStarted;
    private final int defaultThreadsNo=5;

    public Service(UserRepo userRepo, GameRepo gameRepo) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
        observers=new ArrayList<>();
        users=new ArrayList<>();
        this.participants=0;
        this.positions=new HashMap<>();
        this.gameStarted=false;
    }

    @Override
    public User login(IObserver client, String username, String password) {
        if(participants>=2||this.gameStarted==true){
            return null;
        }
        else{
            User user=userRepo.findOne(username, password);
            if(user!=null){
                observers.add(client);
                users.add(username);
                participants+=1;
                if(participants==2){
                    Game game=new Game(this.users);
                    gameRepo.save(game);
                    twoPlayers();
                    gameStarted=true;
                }
            }
            return user;
        }
    }


    @Override
    public void logout(IObserver client, String username) {
        observers.remove(client);
        this.users.remove(username);
        this.participants-=1;
    }

    @Override
    public void updateGame(String winner) {
        Game game=gameRepo.getLast();
        game.setWinner(winner);
        gameRepo.update(game);
    }

    @Override
    public void twoPlayers() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IObserver observer:observers) {
            executor.execute(() -> {
                try {
                    System.out.println("notifying users there are 2 players...");
                    observer.thereAreTwoPlayers(this.users);
                } catch (Exception e) {
                    System.out.println("error notifying users...");
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void actualGameStarted() {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IObserver observer:observers) {
            executor.execute(() -> {
                try {
                    System.out.println("notifying users button START was pressed...");
                    observer.actualGameStarted();
                } catch (Exception e) {
                    System.out.println("error notifying users...");
                }
            });
        }
        executor.shutdown();
    }

    @Override
    public void positionChoosed(String user, Integer position) {
        User player=userRepo.findOneByUsername(user);
        player.setPlanePoz(position);
        userRepo.update(player);
        this.positions.put(user, position);
        if(this.positions.size()==2){
            actualGameStarted();
        }
    }

    @Override
    public void shoot(String user, Integer position) {
        for(Map.Entry<String, Integer> entry:this.positions.entrySet()){
            if(!(entry.getKey().equals(user))){
                if(entry.getValue().equals(position)) {
                    //oponentul a nimerit avionul celuilalt jucator
                    //jocul s-a termnat
                    //se anunta userii castigatorul
                    String winner = user;
                    notifyUsers(winner);
                    gameStarted = false;
                    //participants=0;
                    //this.positions.clear();
                    return;
                }
            }
        }
    }

    private void notifyUsers(String winner){
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IObserver observer:observers) {
            executor.execute(() -> {
                try {
                      System.out.println("notifying users the game has finished...");
                      observer.gameFinished(winner);
                } catch (Exception e) {
                    System.out.println("error notifying users...");
                }
            });
        }
        executor.shutdown();

        Game game=gameRepo.getLast();
        game.setWinner(winner);
        gameRepo.update(game);
    }
}
