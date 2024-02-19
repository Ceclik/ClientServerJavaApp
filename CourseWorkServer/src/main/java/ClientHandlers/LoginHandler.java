package ClientHandlers;

import DAO.CustomerDAO;
import DAO.UserDAO;
import Entities.User;

import java.io.IOException;

public class LoginHandler {
    public LoginHandler() throws IOException {
        MainClientHandler.SendString("Ok");
    }

    public void CheckUser() throws IOException, ClassNotFoundException {
        User receivedUser = (Entities.User) MainClientHandler.getOis().readObject();
        if (UserDAO.AuthenticateUser(receivedUser))
            MainClientHandler.SendString("Success");
        else MainClientHandler.SendString("Failed");
    }

    public void RegisterUser() throws IOException, ClassNotFoundException {
        User receivedUser = null;
        receivedUser = (User) MainClientHandler.getOis().readObject();
        UserDAO.AddUser(receivedUser);
        MainClientHandler.SendString("Success");
    }

    public void SendFullUserData() throws IOException, ClassNotFoundException {
        User receivedUser = (Entities.User) MainClientHandler.getOis().readObject();
        User fullUser = UserDAO.GetUserByBasicData(receivedUser);
        MainClientHandler.getOos().writeObject(fullUser);
        MainClientHandler.getOos().flush();
        if(fullUser.getRole() == 'u') {
            MainClientHandler.getOos().writeObject(CustomerDAO.GetCustomerByBasicData(fullUser.getName(), fullUser.getEmail()));
            MainClientHandler.getOos().flush();
        }
    }

    public void UpdateUserData() throws IOException, ClassNotFoundException {
        User newUser = (Entities.User) MainClientHandler.getOis().readObject();
        UserDAO.UpdateUser(newUser);
    }

    public void DeleteUser() throws IOException, ClassNotFoundException {
        User userToDelete = (Entities.User) MainClientHandler.getOis().readObject();
        UserDAO.DeleteUser(userToDelete);
        MainClientHandler.SendString("Deleted");
    }
}
