package com.example.javafx_food_project;

import com.example.javafx_food_project.Communication.ClientManager;
import com.example.javafx_food_project.misc_classes.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends Application {
    public static String currentScene = "IntroPage";
    //public static String currentScene = "HomepageCustomer";
    public static ClientManager cusClient;

    public static List<Restaurant> restaurants = new ArrayList<>();
    public static List<Image> transitionFramesFromLoginToCusHomepage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(currentScene + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }


}