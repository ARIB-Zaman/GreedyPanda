package com.example.javafx_food_project.user_classes;

public class Customer extends User{
    String PurchaseHistory;
    String cart;
    public Customer(String name, String userEmail, String userPassword) {
        super(name, userEmail, userPassword);
    }
}
