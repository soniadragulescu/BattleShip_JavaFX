package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Game;
import model.User;
import services.IObserver;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MainController extends UnicastRemoteObject implements IObserver, Serializable {
    private IService service;
    private User user;

    public MainController() throws RemoteException {
    }

    public void setUser(User user){
        this.user=user;
    }

    public void setService(IService service){
        this.service=service;
        init();
    }

    @FXML
    Label labelWait;

    @FXML
    Label labelPlayers;

    @FXML
    Label labelMyPlane;

    @FXML
    TextField textboxPosition;

    @FXML
    Button buttonStart;

    @FXML
    Button buttonLogout;

    @FXML
    Button button1;

    @FXML
    Button button2;

    @FXML
    Button button3;

    @FXML
    Button button4;

    @FXML
    Button button5;

    @FXML
    Button button6;

    @FXML
    Button button7;

    @FXML
    Button button8;

    @FXML
    Button button9;



    public void init(){
        buttonStart.setVisible(false);
        labelMyPlane.setVisible(false);
        labelPlayers.setVisible(false);
        textboxPosition.setVisible(false);

        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        button4.setVisible(false);
        button5.setVisible(false);
        button6.setVisible(false);
        button7.setVisible(false);
        button8.setVisible(false);
        button9.setVisible(false);

    }

    @FXML
    public void logout(){
        this.service.logout(this,this.user.getUsername());
        Platform.exit();
    }


    @Override
    public void gameFinished(String winner) throws RemoteException {
        String actualWinner=null;
        if(winner.equals(this.user.getUsername())){
            actualWinner="ESTI CHIAR TU!";
        }
        else{
            actualWinner="este "+winner.toUpperCase()+"!!!";
        }

        String finalActualWinner = actualWinner;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                showErrorMessage("Jocul s-a terminat! Castigatrul "+ finalActualWinner);
            }
        });

    }

    @Override
    public void thereAreTwoPlayers(List<String> players){
        labelWait.setVisible(false);

        labelPlayers.setVisible(true);

        buttonStart.setVisible(true);
        textboxPosition.setVisible(true);

        String oponent="";
        for(String u:players)
            if (!(u.equals(this.user.getUsername()))){
                oponent+=u;
                break;
            }
        String finalOponent = oponent;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelPlayers.setText("Oponent: "+ finalOponent);
            }
        });
    }

    @FXML
    public void startGame(){
        try {
            Integer position = Integer.parseInt(textboxPosition.getText());
            if(position<1||position>9)
                throw new NumberFormatException();
            else{
                buttonStart.setVisible(false);
                textboxPosition.setVisible(false);
                service.positionChoosed(this.user.getUsername(), position);
                this.user.setPlanePoz(position);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        labelMyPlane.setText("My plane: "+position.toString());
                    }
                });
            }
        }
        catch(NumberFormatException ex){
            showErrorMessage("Trebuie ales un numar intre 1 si 9!!!");
        }
    }

    @Override
    public void actualGameStarted() throws RemoteException {
        labelPlayers.setVisible(true);
        labelMyPlane.setVisible(true);

        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        button4.setVisible(true);
        button5.setVisible(true);
        button6.setVisible(true);
        button7.setVisible(true);
        button8.setVisible(true);
        button9.setVisible(true);
    }


    private static void showErrorMessage(String err){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message!");
        message.setContentText(err);
        message.showAndWait();
    }

    @FXML
    public void handleButton1(){
        button1.setVisible(false);
        service.shoot(this.user.getUsername(),1);
    }

    @FXML
    public void handleButton2(){
        button2.setVisible(false);
        service.shoot(this.user.getUsername(),2);
    }

    @FXML
    public void handleButton3(){
        button3.setVisible(false);
        service.shoot(this.user.getUsername(),3);
    }

    @FXML
    public void handleButton4(){
        button4.setVisible(false);
        service.shoot(this.user.getUsername(),4);
    }

    @FXML
    public void handleButton5(){
        button5.setVisible(false);
        service.shoot(this.user.getUsername(),5);
    }

    @FXML
    public void handleButton6(){
        button6.setVisible(false);
        service.shoot(this.user.getUsername(),6);
    }

    @FXML
    public void handleButton7(){
        button7.setVisible(false);
        service.shoot(this.user.getUsername(),7);
    }

    @FXML
    public void handleButton8(){
        button8.setVisible(false);
        service.shoot(this.user.getUsername(),8);
    }

    @FXML
    public void handleButton9(){
        button9.setVisible(false);
        service.shoot(this.user.getUsername(),9);
    }

}
