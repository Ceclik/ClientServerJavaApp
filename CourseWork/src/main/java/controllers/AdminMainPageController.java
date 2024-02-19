package controllers;

import Entities.User;
import Entities.Warehouse;
import Utils.Connectors.Connector;
import Utils.Drawers.AdminMainPageWindowDrawer;
import Utils.Drawers.UserMainPageWindowDrawer;
import Utils.LoggedUserData;
import Utils.SceneManager;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class AdminMainPageController {
    public void OnAllAccountsButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Connector.SendString("getAllAccounts");
        if(((String)Connector.ois.readObject()).equals("Ok")){
            List<User> allUsers = (List<User>) Connector.ois.readObject();
            AdminMainPageWindowDrawer.DrawAccountsListDialog(allUsers);
        }
    }

    public void OnAddAdminButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        AdminMainPageWindowDrawer.DrawAddAdminDialog();
    }

    public void OnChangeLoginButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String previousValue = LoggedUserData.getLogin();
        UserMainPageWindowDrawer.DrawChangeDialogIcon("логин", previousValue);
        LoggedUserData.Update();
    }

    public void OnChangePasswordButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        UserMainPageWindowDrawer.DrawChangePasswordDialog();
        LoggedUserData.Update();
    }

    public void OnExitButtonClick(ActionEvent actionEvent) throws IOException {
        SceneManager.LoadSceneFromMenu(actionEvent, "LogInView.fxml");
    }

    public void OnDeleteAccountButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (LoggedUserData.Delete().equals("Deleted"))
            UserMainPageWindowDrawer.ShowDefaultDialog("Удаление аккаунта", "Аккаунт был успешно удалён");
        SceneManager.LoadSceneFromMenu(actionEvent, "LogInView.fxml");
    }

    public void OnWatchWarehousesButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Connector.SendString("getAllWarehouses");
        if(((String)Connector.ois.readObject()).equals("Ok")){
            AdminMainPageWindowDrawer.DrawWarehouses((List<Warehouse>) Connector.ois.readObject());
        }
    }
}
