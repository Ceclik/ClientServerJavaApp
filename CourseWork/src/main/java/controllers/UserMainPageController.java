package controllers;

import Entities.Order;
import Entities.OrderItem;
import Entities.Product;
import Utils.Connectors.Connector;
import Utils.Drawers.UserMainPageWindowDrawer;
import Utils.LoggedCustomerData;
import Utils.LoggedUserData;
import Utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserMainPageController {

    @FXML
    private Accordion accordion;
    private Accordion cartAccordion;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextField searchTextField;
    private ObservableList<Product> products;
    private final List<Product> cart = new ArrayList<>();


    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Connector.SendString("getAllproducts");
        String receivedString = (String) Connector.ois.readObject();
        if (receivedString.equals("Ok")) {
            products = FXCollections.observableArrayList((List<Product>) Connector.ois.readObject());
            accordion.getPanes().addAll(createTitledPanes(products));
            VBox root = new VBox(accordion);
        }
    }

    private ObservableList<TitledPane> createTitledPanes(ObservableList<Product> products) {
        ObservableList<TitledPane> titledPanes = FXCollections.observableArrayList();

        for (Product product : products) {
            if(product.getAmountInWarehouse() > 0) {
                TitledPane titledPane = new TitledPane();
                titledPane.setText(product.getName());

                // Создаем AnchorPane с информацией о товаре и кнопкой добавления в корзину
                AnchorPane productPane = createProductPane(product);

                // Устанавливаем содержимое TitledPane
                titledPane.setContent(productPane);

                titledPanes.add(titledPane);
            }
        }

        return titledPanes;
    }

    private AnchorPane createProductPane(Product product) {
        AnchorPane anchorPane = new AnchorPane();

        Label DiscriptionLabelLabel = new Label("Описание: " + product.getDiscription());
        Label priceLabel = new Label("Цена: " + product.getPrice());


        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, product.getAmountInWarehouse());
        valueFactory.setValue(1);
        Spinner<Integer> amountOfNeededSpinner = new Spinner<>();
        amountOfNeededSpinner.setValueFactory(valueFactory);
        amountOfNeededSpinner.setPrefWidth(60);
        amountOfNeededSpinner.setPrefHeight(18);


        Button addToCartButton = new Button("Добавить в корзину");
        addToCartButton.setOnAction(e -> {
            Product addingProduct = new Product(product);
            addingProduct.setAmountInWarehouse(amountOfNeededSpinner.getValue());
            cart.add(addingProduct);
        });

        anchorPane.getChildren().addAll(DiscriptionLabelLabel, priceLabel, addToCartButton, amountOfNeededSpinner);

        AnchorPane.setTopAnchor(DiscriptionLabelLabel, 10.0);
        AnchorPane.setLeftAnchor(DiscriptionLabelLabel, 10.0);

        AnchorPane.setTopAnchor(priceLabel, 30.0);
        AnchorPane.setLeftAnchor(priceLabel, 10.0);

        AnchorPane.setBottomAnchor(addToCartButton, 10.0);
        AnchorPane.setLeftAnchor(addToCartButton, 60.0);

        AnchorPane.setBottomAnchor(amountOfNeededSpinner, 10.0);
        AnchorPane.setLeftAnchor(amountOfNeededSpinner, 210.0);

        return anchorPane;
    }

    public void OnWatchButtonClick(ActionEvent event) {

        if (cart.isEmpty()) {
            UserMainPageWindowDrawer.ShowDefaultDialog("Корзина", "В корзине нет товаров!");
        } else {
            UserMainPageWindowDrawer.DrawWatchCartDialog(cart);
        }
    }

    public void OnOrderButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        if (!cart.isEmpty()) {
            float totalPrice = 0;
            for (var product : cart)
                totalPrice += product.getPrice() * product.getAmountInWarehouse();
            Order order = new Order(LoggedCustomerData.getId(), new Date().toString(), totalPrice);
            List<OrderItem> orderItems = new ArrayList<>();
            for (var product : cart)
                orderItems.add(new OrderItem(order, product));

            Connector.SendString("createOrder");
            if (((String) Connector.ois.readObject()).equals("Ok")) {
                Connector.oos.writeObject(orderItems);
                Connector.oos.flush();
                if (((String) Connector.ois.readObject()).equals("next")) {
                    Connector.oos.writeObject(order);
                    Connector.oos.flush();
                }
            }
            UserMainPageWindowDrawer.ShowDefaultDialog("Оформление заказа", "Заказ был успешно оформлен!");
        } else
            UserMainPageWindowDrawer.ShowDefaultDialog("Ошибка при оформлении заказа", "В корзине нет товаров!");
    }

    public void OnClearButtonClick(ActionEvent event) {
        cart.clear();
        UserMainPageWindowDrawer.ShowDefaultDialog("Очистка корзины", "Все товары из корзины были удалены!");
    }

    public void OnSearchIconClick(MouseEvent mouseEvent) {
        String searchRequest = searchTextField.getText();
        if (!searchRequest.isEmpty()) {
            List<Product> searchedList = new ArrayList<>();
            for (var product : products) {
                if (product.getName().toLowerCase().contains(searchRequest.toLowerCase()))
                    searchedList.add(new Product(product));
            }
            accordion.getPanes().clear();
            accordion.getPanes().addAll(createTitledPanes(FXCollections.observableArrayList(searchedList)));
        } else {
            accordion.getPanes().clear();
            accordion.getPanes().addAll(createTitledPanes(products));
        }
    }

    public void OnWatchAccountButtonClick(ActionEvent event) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        System.out.println("Login: " + LoggedUserData.getLogin());
        System.out.println("Name: " + LoggedUserData.getName());

        // Добавляем метки и данные об аккаунте
        gridPane.add(new Label("Логин:"), 0, 0);
        gridPane.add(new Label("Имя:"), 0, 1);
        gridPane.add(new Label("Электронная почта:"), 0, 2);
        gridPane.add(new Label("Адрес:"), 0, 3);

        gridPane.add(new Label(LoggedUserData.getLogin()), 1, 0);
        gridPane.add(new Label(LoggedUserData.getName()), 1, 1);
        gridPane.add(new Label(LoggedUserData.getEmail()), 1, 2);
        gridPane.add(new Label(LoggedUserData.getAdress()), 1, 3);

        // Создаем Alert с типом INFORMATION
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация об аккаунте");
        alert.setHeaderText("Данные аккаунта");
        alert.getDialogPane().setContent(gridPane);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setResizable(true);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
        stage.setWidth(512);  // Начальная ширина
        stage.setHeight(512); // Начальная высота

        // Добавляем кнопку "Ок"
        alert.getButtonTypes().setAll(ButtonType.OK);

        // Показываем диалоговое окно и ждем, пока пользователь его закроет
        alert.showAndWait();
    }

    public void OnChangeLoginButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String previousValue = LoggedUserData.getLogin();
        UserMainPageWindowDrawer.DrawChangeDialogIcon("логин", previousValue);
        LoggedUserData.Update();
    }

    public void OnChangePasswordButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        UserMainPageWindowDrawer.DrawChangePasswordDialog();
        LoggedUserData.Update();
    }

    public void OnChangeNameButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String previousValue = LoggedUserData.getName();
        UserMainPageWindowDrawer.DrawChangeDialogIcon("ФИО", previousValue);
        LoggedUserData.Update();
    }

    public void OnChangeEmailButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String previousValue = LoggedUserData.getEmail();
        UserMainPageWindowDrawer.DrawChangeDialogIcon("Email", previousValue);
        LoggedUserData.Update();
    }

    public void OnChangeAddressButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        String previousValue = LoggedUserData.getAdress();
        UserMainPageWindowDrawer.DrawChangeDialogIcon("Адрес", previousValue);
        LoggedUserData.Update();
    }

    public void OnDeleteAccountButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        if (LoggedUserData.Delete().equals("Deleted"))
            UserMainPageWindowDrawer.ShowDefaultDialog("Удаление аккаунта", "Аккаунт был успешно удалён");
        SceneManager.LoadSceneFromMenu(event, "LogInView.fxml");
    }

    public void OnExitButtonClick(ActionEvent event) throws IOException {
        SceneManager.LoadSceneFromMenu(event, "LogInView.fxml");
    }

    public void OnWatchHistoryButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Connector.SendString("watchOrdersHistory");
        if (((String) Connector.ois.readObject()).equals("Ok")) {
            Connector.oos.writeObject(LoggedCustomerData.getId());
            Connector.oos.flush();
            List<Order> receivedOrders = (List<Order>) Connector.ois.readObject();
            UserMainPageWindowDrawer.DrawHistoryDialog(receivedOrders);
        }
    }

    public void OnClearHistoryButtonClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Connector.SendString("clearHistory");
        if(((String)Connector.ois.readObject()).equals("Ok")){
            Connector.oos.writeObject(LoggedCustomerData.getId());
            Connector.oos.flush();
            UserMainPageWindowDrawer.ShowDefaultDialog("Очистка истории", "История ваших заказов успешно очищена!");
        }
    }
}
