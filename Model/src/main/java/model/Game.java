package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Game")
public class Game implements Serializable {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection
    private List<String> users;

    @Column(name="Winner")
    private String winner;

    public Game() {
        this.users=new ArrayList<>();
        this.winner=null;
    }

    public Game(Integer id,  List<String> users,String winner) {
        this.id = id;
        this.users=users;
        this.winner=winner;
    }

    public Game(List<String> users) {
        this.users=users;
        this.winner=null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
