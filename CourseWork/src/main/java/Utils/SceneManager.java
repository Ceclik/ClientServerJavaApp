package Utils;

import com.example.coursework.ClientApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    public static void LoadScene(ActionEvent event, String sceneName) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(ClientApplication.class.getResource(sceneName)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 512, 512);

        stage.setScene(scene);
        stage.show();
    }

    public static void LoadSceneFromMenu(ActionEvent event, String sceneName) throws IOException{
        MenuItem menuItem = (MenuItem) event.getSource();

        // Получаем контекстное меню, затем его владельца (обычно это PopupMenu),
        // и, наконец, его владельца - это Stage
        ContextMenu popupMenu = menuItem.getParentPopup();
        Stage stage = (Stage) popupMenu.getOwnerWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(ClientApplication.class.getResource(sceneName)));
        Scene scene = new Scene(root, 512, 512);

        stage.setScene(scene);
        stage.show();
    }
}