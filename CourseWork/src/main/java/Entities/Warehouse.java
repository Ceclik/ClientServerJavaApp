package Entities;

import java.io.Serializable;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 5L;
    private Long warehouse_id;
    private String location;
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
