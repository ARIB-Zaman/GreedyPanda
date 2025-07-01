package com.example.javafx_food_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class LoginPage_Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}