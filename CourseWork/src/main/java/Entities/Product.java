package Entities;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long product_id;
    private String name;
    private float price;
    private int amountInWarehouse;
    private String discription;
    private Long warehouseId;

    public Product(String name, float price, int amountInWarehouse, String discription) {
        this.name = name;
        this.price = price;
        this.amountInWarehouse = amountInWarehouse;
        this.discription = discription;
    }

    public Product(Product product){
        this.product_id = product.product_id;
        this.name = product.name;
        this.price = product.price;
        this.amountInWarehouse = product.amountInWarehouse;
        this.discription = product.discription;
    }

    public Product(String name, float price, int amountInWarehouse, String discription, Long warehouseId) {
        this.name = name;
        this.price = price;
        this.amountInWarehouse = amountInWarehouse;
        this.discription = discription;
        this.warehouseId = warehouseId;
    }

    public Product() {
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getAmountInWarehouse() {
        return amountInWarehouse;
    }

    public String getDiscription() {
        return discription;
    }

    public void setAmountInWarehouse(int amountInWarehouse) {
        this.amountInWarehouse = amountInWarehouse;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }
}
