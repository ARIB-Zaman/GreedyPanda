package com.example.javafx_food_project.user_classes;

import java.io.Serializable;

public abstract class User implements Serializable {
    String username;
    String userEmail;
    int userID;
    String userPassword;
    public User(String name, String userEmail, String userPassword){
        this.username = name;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
