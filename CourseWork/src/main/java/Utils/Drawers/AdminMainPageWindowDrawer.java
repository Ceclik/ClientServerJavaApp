package Utils.Drawers;

import Entities.*;
import Utils.Connectors.AccountsConnector;
import Utils.Connectors.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AdminMainPageWindowDrawer {

    public static void DrawAccountsListDialog(List<User> accounts) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Все учётные записи");
        alert.setHeaderText("Список учётных записей: ");

        Accordion accountsAccordion = new Accordion();
        accountsAccordion.getPanes().addAll(createTitledPanesForAccountsView(FXCollections.observableArrayList(accounts)));

        // Добавляем Accordion в диалог
        alert.getDialogPane().setContent(accountsAccordion);

        // Устанавливаем размеры диалогового окна
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(700);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);

        // Создаем кнопку "Ок"
        ButtonType okButton = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

    private static ObservableList<TitledPane> createTitledPanesForAccountsView(ObservableList<User> accounts) throws IOException, ClassNotFoundException, IOException {
        ObservableList<TitledPane> listAccounts = FXCollections.observableArrayList();

        for (var account : accounts) {
            TitledPane titledPane = new TitledPane();
            if (account.getRole() == 'a') {
                titledPane.setText("Логин: " + account.getLogin() + ";  " + "Роль: " + account.getRole());
                titledPane.setCollapsible(false);
                listAccounts.add(titledPane);
            }
            else {
                titledPane.setText("Логин: " + account.getLogin() + ";  " + "Роль: " + account.getRole() +
                        ";  ФИО: " + account.getName() + ";  email: " + account.getEmail() + ";  Адрес: " +
                        account.getAdress());

                Connector.SendString("getCustomerDataByUserData");
                Connector.oos.writeObject(account);
                Customer csmr = (Customer) Connector.ois.readObject();
                Connector.SendString("watchOrdersHistory");
                if (((String) Connector.ois.readObject()).equals("Ok")) {
                    Connector.oos.writeObject(csmr.getId());
                    Connector.oos.flush();
                    List<Order> receivedOrders = (List<Order>) Connector.ois.readObject();


                    // Создаем AnchorPane с информацией о товаре и кнопкой добавления в корзину
                    AnchorPane ordersListPane = createOrderPane(receivedOrders);

                    // Устанавливаем содержимое TitledPane
                    titledPane.setContent(ordersListPane);

                    listAccounts.add(titledPane);
                }
            }
        }
        return listAccounts;
    }

    private static AnchorPane createOrderPane(List<Order> orders) {
        AnchorPane anchorPane = new AnchorPane();

        ObservableList<String> listOrders = FXCollections.observableArrayList();

        for (var order : orders)
            listOrders.add(order.getOrderDate() + ";     " + "Общая стоимость: " + order.getTotalPrice());

        ListView<String> listOrdersShow = new ListView<>(listOrders);
        listOrdersShow.setPrefWidth(370);

        anchorPane.getChildren().add(listOrdersShow);

        return anchorPane;
    }


    public static void DrawAddAdminDialog() throws IOException, ClassNotFoundException {
        // Создаем GridPane для размещения элементов интерфейса
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label loginLabel = new Label("Введите логин:");
        gridPane.add(loginLabel, 0,0);

        TextField loginField = new TextField();
        loginField.setPromptText("Логин");
        gridPane.add(loginField, 1, 0);

        Label firstPasswordLabel = new Label("Введите пароль");
        gridPane.add(firstPasswordLabel, 0, 1);

        PasswordField firstPassword = new PasswordField();
        firstPassword.setPromptText("Пароль");
        gridPane.add(firstPassword, 1, 1);

        Label repeatPasswordLabel = new Label("Повторите пароль: ");
        gridPane.add(repeatPasswordLabel, 0, 2);

        PasswordField secondPassword = new PasswordField();
        secondPassword.setPromptText("Повторите пароль");
        gridPane.add(secondPassword, 1, 2);


        // Кнопка для редактирования значения
        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);

        // Создаем кнопку отмены с вашим текстом
        ButtonType cancelButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Изменение");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        // Добавляем обе кнопки к диалоговому окну
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

        // Ожидаем закрытия диалогового окна только после проверки паролей
        boolean passwordsMatch = false;
        while (!passwordsMatch) {
            // Ожидаем закрытия диалогового окна
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == confirmButtonType) {
                if (firstPassword.getText().equals(secondPassword.getText()) && firstPassword.getText().length() >= 6) {
                    User newAdmin = new User(loginField.getText(), secondPassword.getText(), 'a');
                    AccountsConnector.SendString("register");
                    String receivedString = (String) Connector.ois.readObject();
                    if (receivedString.equals("Ok")) {
                        AccountsConnector.SendEnteredUserData(newAdmin);
                        receivedString = (String) Connector.ois.readObject();
                        System.out.println("Received status: " + receivedString);
                    }
                    passwordsMatch = true;
                } else {
                    System.out.println("Ошибка: Введены некорректные данные");
                    secondPassword.setStyle("-fx-background-color: #f2968f");
                }
            } else {
                System.out.println("Отмена");
                return; // Выход из метода, если нажата кнопка отмены
            }
        }
    }

    public static void DrawWarehouses(List<Warehouse> warehouses) throws IOException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cклады");
        alert.setHeaderText("Список складов: ");

        Accordion accountsAccordion = new Accordion();
        accountsAccordion.getPanes().addAll(createTitledPanesForWarehousesView(FXCollections.observableArrayList(warehouses)));

        // Добавляем Accordion в диалог
        alert.getDialogPane().setContent(accountsAccordion);

        // Создаем кнопки "Ок" и "Добавить склад"
        ButtonType okButton = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        ButtonType addButton = new ButtonType("Добавить склад", ButtonBar.ButtonData.APPLY);

        // Устанавливаем кнопки в диалог
        alert.getButtonTypes().setAll(okButton, addButton);

        // Получаем кнопки для управления
        Button add = (Button) alert.getDialogPane().lookupButton(addButton);

        add.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                AddWarehouse();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Добавить склад");
        });

        // Устанавливаем размеры диалогового окна
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setResizable(true);
        stage.setMinWidth(700);
        stage.setMinHeight(700);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);

        alert.showAndWait();
    }

    private static ObservableList<TitledPane> createTitledPanesForWarehousesView(ObservableList<Warehouse> warehouses) throws IOException, ClassNotFoundException, IOException {
        ObservableList<TitledPane> listWarehouses = FXCollections.observableArrayList();

        for (var warehouse : warehouses) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText("Расположение: " + warehouse.getLocation() + "     Вместимость: " + warehouse.getCapacity());

            Connector.SendString("getAllProductsOfWarehouse");
            if(((String)Connector.ois.readObject()).equals("Ok")) {
                Connector.oos.writeObject(warehouse.getWarehouse_id());
                Connector.oos.flush();

                List<Product> receivedProducts = (List<Product>) Connector.ois.readObject();

                AnchorPane ordersListPane = createProductPane(receivedProducts, warehouse);

                titledPane.setContent(ordersListPane);

                listWarehouses.add(titledPane);
            }
        }
        return listWarehouses;
    }

    private static AnchorPane createProductPane(List<Product> products, Warehouse warehouse) {
        AnchorPane anchorPane = new AnchorPane();

        ObservableList<String> listProducts = FXCollections.observableArrayList();

        for (var product : products)
            listProducts.add(product.getName() + "    Цена: " + product.getPrice() + "    Остаток: " + product.getAmountInWarehouse());

        ListView<String> listProductsShow = new ListView<>(listProducts);
        listProductsShow.setPrefWidth(370);

        Button addProductButton = new Button();
        addProductButton.setText("Добавить товар");
        addProductButton.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            try {
                AddProduct(warehouse);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button increaseAmountOfProduct = new Button();
        increaseAmountOfProduct.setText("Пополнить запас товара");
        increaseAmountOfProduct.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            try {
                increaseAmountOfGood();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        /*Button createReportButton = new Button();
        createReportButton.setText("Создать отчёт");
        createReportButton.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            try {
               *//*TODO*//*
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });*/
/*======================================================================================================================================================================*/
        AnchorPane.setLeftAnchor(addProductButton, 400.0);
        AnchorPane.setTopAnchor(addProductButton, 200.0);

        AnchorPane.setLeftAnchor(increaseAmountOfProduct, 400.0);
        AnchorPane.setTopAnchor(increaseAmountOfProduct, 230.0);

        anchorPane.getChildren().add(listProductsShow);
        anchorPane.getChildren().add(addProductButton);
        anchorPane.getChildren().add(increaseAmountOfProduct);

        return anchorPane;
    }

    private static void CreateReport() throws IOException, ClassNotFoundException {
        Connector.SendString("createReport");
        if(((String)Connector.ois.readObject()).equals("Ok")){

        }
    }

    private static void increaseAmountOfGood() throws IOException, ClassNotFoundException {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Введите наименование товара:");
        gridPane.add(nameLabel, 0, 0);

        TextField nameField = new TextField();
        nameField.setPromptText("Наименование");
        gridPane.add(nameField, 1, 0);

        Label amountLabel = new Label("Введите количество товара: ");
        gridPane.add(amountLabel, 0, 1);

        TextField amountField = new TextField();
        amountField.setPromptText("количество");
        gridPane.add(amountField, 1,1);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);

        ButtonType cancelButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Пополнение запасов товара товара");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            Connector.SendString("fulfillProduct");
            if (((String) Connector.ois.readObject()).equals("Ok")) {
                Connector.SendString(nameField.getText());
                if(((String)Connector.ois.readObject()).equals("next")) {
                    Connector.oos.writeObject(Integer.parseInt(amountField.getText()));
                    Connector.oos.flush();
                }
            }
        }
    }

    private static void AddProduct(Warehouse warehouse) throws IOException, ClassNotFoundException {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Введите наименование товара:");
        gridPane.add(nameLabel, 0, 0);

        TextField nameField = new TextField();
        nameField.setPromptText("Наименование");
        gridPane.add(nameField, 1, 0);

        Label priceLabel = new Label("Введите цену товара: ");
        gridPane.add(priceLabel, 0, 1);

        TextField priceField = new TextField();
        priceField.setPromptText("Цена");
        gridPane.add(priceField, 1, 1);

        Label amountLabel = new Label("Введите количество товара: ");
        gridPane.add(amountLabel, 0, 2);

        TextField amountField = new TextField();
        amountField.setPromptText("количество");
        gridPane.add(amountField, 1,2);

        Label discriptionLabel = new Label("Описние: ");
        gridPane.add(discriptionLabel,0, 3);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Описание товара");
        gridPane.add(descriptionArea, 1,3);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);

        ButtonType cancelButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Добавление товара");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            Connector.SendString("addProduct");
            if (((String) Connector.ois.readObject()).equals("Ok")) {
                Product newProduct = new Product(nameField.getText(), Float.parseFloat(priceField.getText()),
                        Integer.parseInt(amountField.getText()), descriptionArea.getText(), warehouse.getWarehouse_id());
                Connector.oos.writeObject(newProduct);
                Connector.oos.flush();
            }
        }
    }

    private static void AddWarehouse() throws IOException, ClassNotFoundException {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label locationLabel = new Label("Введите местоположение склада:");
        gridPane.add(locationLabel, 0, 0);

        TextField locationField = new TextField();
        locationField.setPromptText("Город");
        gridPane.add(locationField, 1, 0);

        Label capacityLabel = new Label("Введите вместимость склада");
        gridPane.add(capacityLabel, 0, 1);

        TextField capacityField = new TextField();
        capacityField.setPromptText("Вместимость");
        gridPane.add(capacityField, 1, 1);

        ButtonType confirmButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);

        ButtonType cancelButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Добавление склада");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

        Button confirmButton = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            Connector.SendString("addWarehouse");
            if (((String) Connector.ois.readObject()).equals("Ok")) {
                Warehouse newWarehouse = new Warehouse(locationField.getText(), Integer.parseInt(capacityField.getText()));
                Connector.oos.writeObject(newWarehouse);
                Connector.oos.flush();
            }
        }
    }
}
