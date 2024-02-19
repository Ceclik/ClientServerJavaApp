package ClientHandlers;

import DAO.CustomerDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.WarehouseDAO;
import Entities.User;
import Launchers.Launch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainClientHandler implements Runnable {

    private static Socket socket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public MainClientHandler(Socket socket) throws IOException {
        MainClientHandler.socket = socket;
    }

    public static void SendString(String string) throws IOException {
        oos.writeObject(string);
        oos.flush();
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

    public static ObjectInputStream getOis() {
        return ois;
    }


    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            while (socket.isConnected()) {
                String receivedPurpose = (String) ois.readObject();
                System.out.println("String " + receivedPurpose + " received!");
                switch (receivedPurpose) {
                    case "login": {
                        LoginHandler loginHandler = new LoginHandler();
                        loginHandler.CheckUser();
                        break;
                    }
                    case "register": {
                        LoginHandler loginHandler = new LoginHandler();
                        loginHandler.RegisterUser();
                        break;
                    }
                    case "getUserData":{
                        LoginHandler loginHandler = new LoginHandler();
                        loginHandler.SendFullUserData();
                        break;
                    }
                    case "updateUser":{
                        LoginHandler loginHandler = new LoginHandler();
                        loginHandler.UpdateUserData();
                        break;
                    }
                    case "deleteUser":{
                        LoginHandler loginHandler = new LoginHandler();
                        loginHandler.DeleteUser();
                        break;
                    }
                    case "getAllproducts":{
                        ProductsHandler productsHandler = new ProductsHandler();
                        productsHandler.GetAllProductsInfo();
                        break;
                    }
                    case "createOrder":{
                        OrdersHandler ordersHandler = new OrdersHandler();
                        ordersHandler.CreateOrder();
                        break;
                    }
                    case "watchOrdersHistory":{
                        OrdersHandler ordersHandler = new OrdersHandler();
                        ordersHandler.WatchHistory();
                        break;
                    }
                    case "clearHistory":{
                        OrdersHandler ordersHandler = new OrdersHandler();
                        OrderDAO.DeleteAllOrdersOfCustomer((Long) MainClientHandler.getOis().readObject());
                        break;
                    }
                    /*=============================  ADMIN FUNCTIONALITY  =================================*/
                    case "getAllAccounts": {
                        AccountsHandler accountsHandler = new AccountsHandler();
                        accountsHandler.SendAllAccounts();
                        break;
                    }
                    case "getCustomerDataByUserData":{
                        User fullUser = (User)ois.readObject();
                        oos.writeObject(CustomerDAO.GetCustomerByBasicData(fullUser.getName(), fullUser.getEmail()));
                        oos.flush();
                        break;
                    }
                    case "getAllWarehouses": {
                        WarehousesHandler warehousesHandler = new WarehousesHandler();
                        warehousesHandler.GetAllWarehouses();
                        break;
                    }
                    case "getAllProductsOfWarehouse":{
                        ProductsHandler productsHandler = new ProductsHandler();
                        productsHandler.GetAllProductsOfWarehouse();
                        break;
                    }
                    case "addWarehouse":{
                        WarehousesHandler warehousesHandler = new WarehousesHandler();
                        warehousesHandler.AddWarehouse();
                        break;
                    }
                    case "addProduct":{
                        ProductsHandler productsHandler = new ProductsHandler();
                        productsHandler.AddProduct();
                        break;
                    }
                    case "fulfillProduct":{
                        ProductsHandler productsHandler = new ProductsHandler();
                        productsHandler.FulfillProductAmount();
                        break;
                    }
                    case "createReport":{
                        OrdersHandler ordersHandler = new OrdersHandler();
                        ordersHandler.CreateReport();
                        break;
                    }
                    default:
                        System.out.println("Wrong purpose!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client " + Launch.getCounter() + " disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
        } finally {
            try {
                oos.close();
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace(); // Обработайте исключение в соответствии с вашими потребностями
            }
            Launch.setCounter(Launch.getCounter() - 1);
        }
    }
}
