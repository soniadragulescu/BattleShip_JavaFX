package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="User")
public class User implements Serializable {
    private String username;
    private String password;
    private Integer planePoz;

    public User(){

    }

    public User(String username, String password, Integer planePoz) {
        this.username = username;
        this.password = password;
        this.planePoz = planePoz;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.planePoz=0;
    }

    @Id
    @Column(name="Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="PlanePoz")
    public Integer getPlanePoz() {
        return planePoz;
    }

    public void setPlanePoz(Integer planePoz) {
        this.planePoz = planePoz;
    }
}
