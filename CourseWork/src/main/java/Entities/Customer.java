package Entities;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 3L;
    private  Long id;
    private  String name;
    private  String email;
    private  String address;

    public Customer(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Customer() {
    }

    public Customer(Customer customer) {
        id = customer.id;
        name = customer.name;
        email = customer.email;
        address = customer.address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
