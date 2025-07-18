package com.example.javafx_food_project;

import com.example.javafx_food_project.Communication.ClientManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx_food_project.LoginPage.currentScene;
import static com.example.javafx_food_project.LoginPage.cusClient;

public class IntroPageController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button customerLoginButton;

    @FXML
    private void onCustomerLoginButton(){

    }

    public void onCustomerLoginButtonClick(ActionEvent e) throws IOException {
        cusClient = new ClientManager();
        switchToCustomerLoginScene(e);

    }


    public void switchToCustomerLoginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        currentScene = "LoginPage";

    }
}
