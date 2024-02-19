package Entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "warehouses")
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouse_id;
    @Column(name = "location")
    private String location;
    @Column(name = "capacity")
    private int capacity;

    public Warehouse() {
    }

    public Warehouse(String location, int capacity) {
        this.location = location;
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public Long getWarehouse_id() {
        return warehouse_id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
