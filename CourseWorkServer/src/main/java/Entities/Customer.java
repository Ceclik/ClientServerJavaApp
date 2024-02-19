package Entities;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 3L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private  Long id;
    @Column(name = "name")
    private  String name;
    @Column(name = "email")
    private  String email;
    @Column(name = "adress")
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
