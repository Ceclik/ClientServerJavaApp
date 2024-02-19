package Utils.Drawers;

import Entities.Order;
import Entities.Product;
import Entities.User;
import Utils.LoggedCustomerData;
import Utils.LoggedUserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class UserMainPageWindowDrawer {

    public static void ShowDefaultDialog(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(text);
        ButtonType okButton = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    public static void DrawChangeDialogIcon(String fieldName, String previousValue) {
        // Создаем GridPane для размещения элементов интерфейса
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        // Добавляем метку и предыдущее значение
        Label label = new Label("Предыдущее значение: ");
        gridPane.add(label, 0, 0);

        Label prevLabel = new Label(previousValue);
        gridPane.add(prevLabel, 1, 0);

        Label newLabel = new Label("Новое значение: ");
        gridPane.add(newLabel, 0, 1);

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

        // Устанавливаем предыдущее значение в текстовое поле
        TextField newValueTextField = new TextField();
        newValueTextField.setPromptText("Новый " + fieldName);
        gridPane.add(newValueTextField, 1, 1);

        // Ожидаем закрытия диалогового окна
        Optional<ButtonType> result = dialog.showAndWait();

        // Обрабатываем введенное значение
        result.ifPresent(buttonType -> {
            if (buttonType == confirmButtonType) {
                switch (fieldName) {
                    case "логин":
                        LoggedUserData.setLogin(newValueTextField.getText());
                        break;
                    case "ФИО":
                        LoggedUserData.setName(newValueTextField.getText());
                        LoggedCustomerData.setName(newValueTextField.getText());
                        break;
                    case "Email":
                        LoggedUserData.setEmail(newValueTextField.getText());
                        LoggedCustomerData.setEmail(newValueTextField.getText());
                        break;
                    case "Адрес":
                        LoggedUserData.setAdress(newValueTextField.getText());
                        LoggedCustomerData.setAddress(newValueTextField.getText());
                        break;
                }
            }
        });
    }

    public static void DrawChangePasswordDialog() {
        // Создаем GridPane для размещения элементов интерфейса
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        // Добавляем метку и предыдущее значение
        Label label = new Label("Новый пароль: ");
        gridPane.add(label, 0, 0);

        PasswordField firstPassword = new PasswordField();
        firstPassword.setPromptText("Введите пароль");
        gridPane.add(firstPassword, 1, 0);

        Label newLabel = new Label("Повторите новый пароль: ");
        gridPane.add(newLabel, 0, 1);

        PasswordField secondPassword = new PasswordField();
        secondPassword.setPromptText("Повторите пароль");
        gridPane.add(secondPassword, 1, 1);


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
                    LoggedUserData.setPassword(secondPassword.getText());
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

    public static void DrawHistoryDialog(List<Order> orders) {
        if (orders.isEmpty()) {
            UserMainPageWindowDrawer.ShowDefaultDialog("История покупок", "Вы ещё ничего не заказывали");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("История покупок");
            alert.setHeaderText("Осуществлённые покупки: ");

            ObservableList<String> listOrders = FXCollections.observableArrayList();

            for (var order : orders)
                listOrders.add(order.getOrderDate() + ";     " + "Общая стоимость: " + order.getTotalPrice());

            ListView<String> listOrdersShow = new ListView<>(listOrders);

            // Добавляем Accordion в диалог
            alert.getDialogPane().setContent(listOrdersShow);

            // Устанавливаем размеры диалогового окна
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setResizable(true);
            stage.setMinWidth(600);
            stage.setMinHeight(400);
            stage.setMaxWidth(800);
            stage.setMaxHeight(600);
            stage.setWidth(700);  // Начальная ширина
            stage.setHeight(500); // Начальная высота

            // Создаем кнопку "Ок"
            ButtonType okButton = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            alert.showAndWait();
        }
    }

    public static void DrawWatchCartDialog(List<Product> cart) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Корзина");
        alert.setHeaderText("Добавленные товары: ");

        Accordion cartAccordion = new Accordion();
        cartAccordion.getPanes().addAll(createTitledPanesForCartView(FXCollections.observableArrayList(cart), cart, cartAccordion));

        // Добавляем Accordion в диалог
        alert.getDialogPane().setContent(cartAccordion);

        // Устанавливаем размеры диалогового окна
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setResizable(true);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMaxWidth(800);
        stage.setMaxHeight(600);
        stage.setWidth(700);  // Начальная ширина
        stage.setHeight(500); // Начальная высота

        // Создаем кнопку "Ок"
        ButtonType okButton = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }

    private static ObservableList<TitledPane> createTitledPanesForCartView(ObservableList<Product> products, List<Product> cart, Accordion cartAccordion) {
        ObservableList<TitledPane> titledPanes = FXCollections.observableArrayList();

        for (Product product : products) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(product.getName());

            // Создаем AnchorPane с информацией о товаре и кнопкой добавления в корзину
            AnchorPane productPane = createProductPaneForCartView(product, titledPane, cart, cartAccordion);

            // Устанавливаем содержимое TitledPane
            titledPane.setContent(productPane);

            titledPanes.add(titledPane);
        }

        return titledPanes;
    }

    private static AnchorPane createProductPaneForCartView(Product product, TitledPane titledPane, List<Product> cart, Accordion cartAccordion) {
        AnchorPane anchorPane = new AnchorPane();

        Label DiscriptionLabelLabel = new Label("Описание: " + product.getDiscription());
        Label priceLabel = new Label("Цена: " + product.getPrice());
        Label amountLabel = new Label("Количество: " + product.getAmountInWarehouse());

        Button DeleteFromCartButton = new Button("Удалить из корзины");
        DeleteFromCartButton.setOnAction(e -> {
            cart.remove(product);
            cartAccordion.getPanes().remove(titledPane);
        });

        anchorPane.getChildren().addAll(DiscriptionLabelLabel, priceLabel, amountLabel, DeleteFromCartButton);

        AnchorPane.setTopAnchor(DiscriptionLabelLabel, 10.0);
        AnchorPane.setLeftAnchor(DiscriptionLabelLabel, 10.0);

        AnchorPane.setTopAnchor(priceLabel, 30.0);
        AnchorPane.setLeftAnchor(priceLabel, 10.0);

        AnchorPane.setTopAnchor(amountLabel, 50.0);
        AnchorPane.setLeftAnchor(amountLabel, 10.0);

        AnchorPane.setBottomAnchor(DeleteFromCartButton, 10.0);
        AnchorPane.setLeftAnchor(DeleteFromCartButton, 120.0);


        return anchorPane;
    }

}
