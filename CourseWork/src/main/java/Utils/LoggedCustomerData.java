package Utils;

import Entities.Customer;

public class LoggedCustomerData {
    private static Long id;
    private static String name;
    private static String email;
    private static String address;

    public static void initData(Customer customer){
        id = customer.getId();
        name = customer.getName();
        email = customer.getEmail();
        address = customer.getAddress();
    }

    public static Long getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoggedCustomerData.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        LoggedCustomerData.email = email;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        LoggedCustomerData.address = address;
    }
}
