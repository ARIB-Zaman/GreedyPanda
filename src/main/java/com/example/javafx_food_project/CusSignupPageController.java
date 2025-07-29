package com.example.javafx_food_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx_food_project.LoginPage.currentScene;
import static com.example.javafx_food_project.LoginPage.cusClient;

public class CusSignupPageController {
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;
    @FXML
    private Label errorLabel;

    @FXML
    public void onSubmitButtonClick(ActionEvent event) throws IOException {
        boolean okay = false;
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        if(username.length() > 3 && email.length() > 3 && password.length() > 5){
            okay = true;
        }
        if (cusClient.validForSignup(username, email, password) && okay){
            cusClient.signupNewCustomer(username, email, password);
            switchToHomepageCustomer(event);

        }
        else if(!okay){
            errorLabel.setText("Username or Email or Password too short");
        }
        else{
            errorLabel.setText("Username or Email already in use");

        }
    }
    public void switchToHomepageCustomer(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/AfterLoginTransition.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        currentScene = "AfterLoginTransition";
    }
}
