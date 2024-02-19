package Entities;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String name;
    private String email;
    private String adress;
    private char role;
    private static final long serialVersionUID = 104413990234785730L;

    public User() {
    }

    public User(User user){
        id = user.id;
        login = user.login;
        password = user.password;
        name = user.name;
        email = user.email;
        adress = user.adress;
        role = user.role;
    }

    public User(Long id, String login, String password, String name, String email, String adress, char role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.role = role;
    }

    public User(String login, String password, String name, String email, String adress, char role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, char role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public char getRole() {
        return role;
    }
}
