package Entities;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "products")
public class Product implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long product_id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "amount_in_warehouse")
    private int amountInWarehouse;
    @Column(name = "discription")
    private String discription;
    @Column(name = "warehouse_id")
    private Long warehouseId;

    public Product(String name, float price, int amountInWarehouse, String discription) {
        this.name = name;
        this.price = price;
        this.amountInWarehouse = amountInWarehouse;
        this.discription = discription;
    }

    public Product(Product product){
        this.name = product.name;
        this.price = product.price;
        this.amountInWarehouse = product.amountInWarehouse;
        this.discription = product.discription;
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
