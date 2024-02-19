package Entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long order_id;
    @Column(name = "customer_id")
    private Long customer_id;
    @Column(name = "order_date")
    private String orderDate;
    @Column(name = "total_price")
    private float totalPrice;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order(Long order_id, Long customer_id, String orderDate, float totalPrice) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Order(String orderDate) {
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public Order(Order order){
        order_id = order.order_id;
        customer_id = order.customer_id;
        orderDate = order.orderDate;
    }
}
