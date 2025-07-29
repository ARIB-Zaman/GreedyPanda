package com.example.javafx_food_project.user_classes;

import java.io.Serializable;

public class RestaurantManager extends User implements Serializable {
    public RestaurantManager(String name, String userEmail, String userPassword) {
        super(name, userEmail, userPassword);
    }
}
