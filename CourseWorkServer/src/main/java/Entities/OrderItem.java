package Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "orderitems")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long order_item_id;
    @Column(name = "order_id")
    private Long order_id;
    @Column(name = "product_id")
    private Long product_id;
    @Column(name = "amount_in_order")
    private int amountInOrder;

    public OrderItem(Long order_item_id, Long order_id, Long product_id, int amountInOrder) {
        this.order_item_id = order_item_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.amountInOrder = amountInOrder;
    }

    public OrderItem(int amountInOrder) {
        this.amountInOrder = amountInOrder;
    }

    public OrderItem() {
    }

    public OrderItem(OrderItem orderItem) {
        order_item_id = orderItem.order_item_id;
        order_id = orderItem.order_id;
        product_id = orderItem.product_id;
        amountInOrder = orderItem.amountInOrder;
    }

    public Long getOrder_item_id() {
        return order_item_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }

    public void setAmountInOrder(int amountInOrder) {
        this.amountInOrder = amountInOrder;
    }
}
