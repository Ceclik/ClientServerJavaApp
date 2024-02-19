package Launchers;

import DAO.UserDAO;
import Entities.User;

import java.util.Scanner;

public class DBTest {
    public static void main(String[] args){
        Scanner scn = new Scanner(System.in);
        System.out.print("Login: ");
        String login = scn.nextLine();
        System.out.print("Password: ");
        String password = scn.nextLine();
        char role = 'u';
        boolean isLocked = false;

       /* User user = new User(login, password, role, isLocked);
        UserDAO.AddUser(user);
        if(UserDAO.AuthenticateUser(user.getLogin(), user.getPassword()))
            System.out.println("Success");
        else System.out.println("Failed");*/
    }
}
