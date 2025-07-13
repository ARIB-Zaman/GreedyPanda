package com.example.javafx_food_project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.javafx_food_project.LoginPage.currentScene;


public class AfterLoginTransitionController {
    @FXML
    private ImageView transitionImage;

    private final List<Image> frames = new ArrayList<>();
    private int currentFrame = 0;
    private Timeline timeline;
    private Parent loginUI;
    private Parent homepageUI;
    @FXML
    private StackPane mainStack;

    @FXML
    public void initialize() {
        transitionImage.setImage(new Image(getClass().getResourceAsStream("/trframes1/tr0001.jpg")));
        loadEverything();
        playTransitionAnimation();
        transitionToHomepage();
    }

    private void preloadFrames() {
        for (int i = 1; i <= 32; i++) {
            String fileName = String.format("/trframes1/tr%04d.jpg", i);
            frames.add(new Image(getClass().getResourceAsStream(fileName)));
        }
    }

    private void playTransitionAnimation() {
        try {
            timeline = new Timeline(new KeyFrame(Duration.millis(42), e -> {
            transitionImage.setImage(frames.get(currentFrame++));
            if (currentFrame >= frames.size()) {
                timeline.stop();
                goToHomePage();
            }
        }));
        timeline.setCycleCount(frames.size());
        timeline.play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void goToHomePage() {
        try {
            currentScene = "HomepageCustomer";
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/HomepageCustomer.fxml"));
            Stage stage = (Stage) transitionImage.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadEverything(){
        preloadFrames();
        preloadPages();
    }
    private void preloadPages() {
        try {
            loginUI = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/LoginPage.fxml"));
            homepageUI = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/HomepageCustomer.fxml"));
            mainStack.getChildren().add(loginUI); // Show login UI first
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void transitionToHomepage() {
        mainStack.getChildren().add(homepageUI);

        TranslateTransition loginOut = new TranslateTransition(Duration.millis(1000), loginUI);
        loginOut.setByY(-750);
        FadeTransition loginFade = new FadeTransition(Duration.millis(1200), loginUI);
        loginFade.setToValue(0);

        TranslateTransition homeIn = new TranslateTransition(Duration.millis(1200), homepageUI);
        homeIn.setFromY(750);
        homeIn.setToY(0);
        FadeTransition homeFade = new FadeTransition(Duration.millis(1200), homepageUI);
        homeFade.setFromValue(0);
        homeFade.setToValue(1);

        loginOut.setOnFinished(e -> {
            mainStack.getChildren().remove(loginUI); // optional: remove login after transition
        });

        new ParallelTransition(loginOut, loginFade, homeIn, homeFade).play();
    }

}

