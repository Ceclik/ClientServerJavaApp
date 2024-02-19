package Entities;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private static final long serialVersionUID = 3L;

    private Long order_item_id;

    private Long order_id;

    private Long product_id;

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

    public OrderItem(Order order, Product product){
        order_id = order.getOrder_id();
        product_id = product.getProduct_id();
        amountInOrder = product.getAmountInWarehouse();
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public int getAmountInOrder() {
        return amountInOrder;
    }

    public void setAmountInOrder(int amountInOrder) {
        this.amountInOrder = amountInOrder;
    }
}