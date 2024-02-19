package controllers;

import Entities.Customer;
import Entities.User;
import Utils.Connectors.Connector;
import Utils.Connectors.AccountsConnector;
import Utils.LoggedCustomerData;
import Utils.LoggedUserData;
import Utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class LoginController {

    public AnchorPane loginAnchorPane;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorTextLabel;

    public void OnLoginButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        User user = new User(loginField.getText(), passwordField.getText());
        AccountsConnector.SendString("login");
        String receivedString = (String) Connector.ois.readObject();
        if (receivedString.equals("Ok")) {
            AccountsConnector.SendEnteredUserData(user);
            receivedString = (String) Connector.ois.readObject();
            System.out.println("Received status: " + receivedString);
            switch (receivedString) {
                case "Success":
                    errorTextLabel.setTextFill(Color.GREEN);
                    errorTextLabel.setText("Вход выполнен успешно!");
                    errorTextLabel.setVisible(true);
                    Connector.SendString("getUserData");
                    if(Connector.ois.readObject().equals("Ok")) {
                        AccountsConnector.SendEnteredUserData(user);
                        User fullLoggedUserData = (User) Connector.ois.readObject();
                        LoggedUserData.initData(fullLoggedUserData);
                        if (LoggedUserData.getRole() == 'u') {
                            Customer fullCustomer = (Customer) Connector.ois.readObject();
                            LoggedCustomerData.initData(fullCustomer);
                            SceneManager.LoadScene(actionEvent, "UserMainPage.fxml");
                        } else
                            SceneManager.LoadScene(actionEvent, "AdminMainView.fxml");
                        break;
                    }
                case "Failed":
                    errorTextLabel.setTextFill(Color.RED);
                    errorTextLabel.setText("Вход не выполнен!");
                    errorTextLabel.setVisible(true);
                    break;
            }
        }
    }

    public void OnRegisterButtonClick(ActionEvent actionEvent) throws IOException {
        SceneManager.LoadScene(actionEvent, "RegisterView.fxml");
    }
}