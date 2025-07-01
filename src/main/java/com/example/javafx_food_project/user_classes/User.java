package com.example.javafx_food_project.user_classes;

public abstract class User {
    String username;
    String userEmail;
    int userID;
    String userPassword;
    public User(String name, String userEmail, String userPassword){
        this.username = name;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
    public boolean verifyPasswordByUsername(String name, String pass){
        if(this.username == name && this.userPassword == pass) return true;
        return false;
    }
}
