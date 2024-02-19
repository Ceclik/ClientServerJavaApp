package Utils.Connectors;

import Entities.Order;

import java.io.IOException;

public class OrdersDataConnector extends Connector{

    public static void SendOrder(Order order) throws IOException {
        oos.writeObject(order);
        oos.flush();
    }
}
