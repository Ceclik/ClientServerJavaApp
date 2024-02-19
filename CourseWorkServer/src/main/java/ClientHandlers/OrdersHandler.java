package ClientHandlers;

import DAO.*;
import Entities.Customer;
import Entities.Order;
import Entities.OrderItem;
import Entities.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersHandler {

    public OrdersHandler() throws IOException {
        MainClientHandler.SendString("Ok");
    }

    public void CreateOrder() throws IOException, ClassNotFoundException {
        List<OrderItem> receivedOrderItems = (List<OrderItem>) MainClientHandler.getOis().readObject();
        MainClientHandler.SendString("next");
        Order receivedOrder = (Order) MainClientHandler.getOis().readObject();
        Long order_id = OrderDAO.AddOrder(receivedOrder);
        for(var orderItem : receivedOrderItems){
            orderItem.setOrder_id(order_id);
            OrderItemDAO.AddOrderItem(orderItem);
            ProductDAO.UpdateAmount(orderItem.getProduct_id(), orderItem.getAmountInOrder(), "order");
            WarehouseDAO.UpdateCapacity(ProductDAO.GetById(orderItem.getProduct_id()).getWarehouseId(), orderItem.getAmountInOrder(), "order");
        }
    }

    public void WatchHistory() throws IOException, ClassNotFoundException {
        Long customerID = (Long)MainClientHandler.getOis().readObject();
        MainClientHandler.getOos().writeObject(OrderDAO.GetAllOrdersOfCustomer(customerID));
        MainClientHandler.getOos().flush();
    }

    public void CreateReport(){
        List<Order> allOrders = OrderDAO.GetAllOrders();
        List<String> results = new ArrayList<>();
        for(var order : allOrders){
            Customer customer = CustomerDAO.GetCustomerById(order.getCustomer_id());
            results.add(order.getOrderDate() + "Клиент: " + customer.getName() + "Сумма: " + order.getTotalPrice());

        }

    }
}
