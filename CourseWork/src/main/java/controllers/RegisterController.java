package controllers;

import Entities.User;
import Utils.Connectors.Connector;
import Utils.Connectors.AccountsConnector;
import Utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

public class RegisterController {
    public Button registerButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField adressField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Label ErrorTextLabel;

    public void OnRegisterButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
        if (CheckAllFields())
            RegisterOperation();
    }

    private void RegisterOperation() throws IOException, ClassNotFoundException {
        User user = new User(loginField.getText(), passwordField.getText(), nameField.getText(), emailField.getText(), adressField.getText(), 'u');
        AccountsConnector.SendString("register");
        String receivedString = (String) Connector.ois.readObject();
        if (receivedString.equals("Ok")) {
            AccountsConnector.SendEnteredUserData(user);
            receivedString = (String) Connector.ois.readObject();
            System.out.println("Received status: " + receivedString);
            switch (receivedString) {
                case "Success":
                    ErrorTextLabel.setTextFill(Color.rgb(124, 212, 114));
                    ErrorTextLabel.setText("Регистрация прошла успешно!");
                    ErrorTextLabel.setVisible(true);
                    registerButton.setDisable(true);
                    break;
                case "Failed":
                    ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
                    ErrorTextLabel.setText("Ошибка при регистрации!");
                    ErrorTextLabel.setVisible(true);
                    break;
            }
        }
    }

    private boolean CheckAllFields() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || adressField.getText().isEmpty()) {
            ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
            ErrorTextLabel.setText("Заполните все поля!");
            ErrorTextLabel.setVisible(true);
            return false;
        } else if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
            ErrorTextLabel.setText("Пароли не совпадают!");
            ErrorTextLabel.setVisible(true);
            return false;
        } else if (passwordField.getText().length() < 6) {
            ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
            ErrorTextLabel.setText("Пароль должен быть длиннее 6 символов!");
            ErrorTextLabel.setVisible(true);
            return false;
        } else if (!emailField.getText().contains("@")) {
            ErrorTextLabel.setTextFill(Color.rgb(245, 50, 50));
            ErrorTextLabel.setText("Введите корректный адрес электронной почты!");
            ErrorTextLabel.setVisible(true);
            return false;
        } else {
            ErrorTextLabel.setTextFill(Color.GREEN);
            ErrorTextLabel.setText("Всё ок!");
            return true;
        }

    }

    public void OnBackButtonClick(ActionEvent actionEvent) throws IOException {
        SceneManager.LoadScene(actionEvent, "LogInView.fxml");
    }
}
