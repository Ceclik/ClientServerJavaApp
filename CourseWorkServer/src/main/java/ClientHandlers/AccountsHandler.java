package ClientHandlers;

import DAO.UserDAO;

import java.io.IOException;

public class AccountsHandler {

    public AccountsHandler() throws IOException {
        MainClientHandler.SendString("Ok");
    }

    public void SendAllAccounts() throws IOException {
        MainClientHandler.getOos().writeObject(UserDAO.GetAllUsers());
        MainClientHandler.getOos().flush();
    }
}
