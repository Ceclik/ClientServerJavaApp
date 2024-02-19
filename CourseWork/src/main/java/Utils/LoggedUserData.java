package Utils;

import Entities.User;
import Utils.Connectors.AccountsConnector;
import Utils.Connectors.Connector;

import java.io.IOException;

public class LoggedUserData {
    private static Long id;
    private static String login;
    private static String password;
    private static String name;
    private static String email;
    private static String adress;
    private static char role;

    public static void initData(User user){
        id = user.getId();
        login = user.getLogin();
        password = user.getPassword();
        name = user.getName();
        email = user.getEmail();
        adress = user.getAdress();
        role = user.getRole();
    }

    public static void Update() throws IOException, ClassNotFoundException {
        Connector.SendString("updateUser");
        if(((String)Connector.ois.readObject()).equals("Ok"))
            AccountsConnector.SendEnteredUserData(new User(id, login, password, name, email, adress, role));
    }

    public static String Delete() throws IOException, ClassNotFoundException {
        Connector.SendString("deleteUser");
        if(((String)Connector.ois.readObject()).equals("Ok"))
            AccountsConnector.SendEnteredUserData(new User(id, login, password, name, email, adress, role));
        return (String)Connector.ois.readObject();
    }

    public static Long getId() {
        return id;
    }

    public static void setId(Long id) {
        LoggedUserData.id = id;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        LoggedUserData.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        LoggedUserData.password = password;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoggedUserData.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        LoggedUserData.email = email;
    }

    public static String getAdress() {
        return adress;
    }

    public static void setAdress(String adress) {
        LoggedUserData.adress = adress;
    }

    public static char getRole() {
        return role;
    }

    public static void setRole(char role) {
        LoggedUserData.role = role;
    }
}
