package com.example.coursework;

import Utils.Connectors.Connector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientApplication extends Application {
    public static void main(String[] args) throws IOException {
        Connector.socket = new Socket("localhost", 8080);
        Connector.oos = new ObjectOutputStream(Connector.socket.getOutputStream());
        Connector.ois = new ObjectInputStream(Connector.socket.getInputStream());
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("LogInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 512, 512);
        stage.setTitle("Warehouse Control");
        stage.setScene(scene);
        stage.show();
    }
}
