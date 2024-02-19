package Utils.Connectors;

import Entities.User;

import java.io.IOException;

public class AccountsConnector extends Connector{

    public static void SendEnteredUserData(User user) throws IOException {
        oos.writeObject(user);
        oos.flush();
    }
}
