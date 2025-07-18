package com.example.javafx_food_project;

import com.example.javafx_food_project.misc_classes.FoodCategory;
import com.example.javafx_food_project.misc_classes.Restaurant;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.example.javafx_food_project.LoginPage.currentScene;
import static com.example.javafx_food_project.LoginPage.restaurants;
import static com.example.javafx_food_project.LoginPage_Controller.*;

public class HomepageCustomerController {
    @FXML
    private ImageView bgimageview2;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane accountinfopane;
    @FXML
    private Button accountinfobutton;
    @FXML
    private ImageView Accountinfoimgview;
    @FXML
    private ListView<Restaurant> restListView;
    @FXML
    private ListView<FoodCategory> foodCategoryListView;


    private int currentFrame = 32;
    List<Image> frames = new ArrayList<>();
    private boolean accountInfoOpen = false;
    List<FoodCategory> foodCategories = new ArrayList<>();

    private void preloadFrames() {
        for (int i = 32; i <= 80; i++) {
            String frameName = String.format("/bgframes2/bg%04d.jpg", i);
            try {
                Image image = new Image(getClass().getResource(frameName).toExternalForm());
                frames.add(image);
            } catch (Exception e) {
                System.err.println("Could not preload: " + frameName);
            }
        }
    }
    private void updateFrame() {
        if (!frames.isEmpty() && currentScene.equals("HomepageCustomer")) {
            try{

                bgimageview2.setImage(frames.get(currentFrame));
                Image blr = cropAndBlurImage(frames.get(currentFrame), accountinfopane, 25);
                Accountinfoimgview.setImage(blr);
            } catch (Exception e) {}

            currentFrame = ((currentFrame+1)%49)+32;
        }
    }

    @FXML
    public void initialize(){
        Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Regular.otf"), 12);
        //grabYourMunch.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 50));

        Image image = new Image(getClass().getResourceAsStream("/trframes1/tr0032.jpg"));
        if(currentScene.equals("HomepageCustomer")) {
            bgimageview2.setImage(image);
            loadFoodCategoryclasses();
            putTextinPane(pane1, "Grab Your Munches", 23, 75);
            preloadFrames();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(42), e -> updateFrame())); // 24 FPS
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            restListView.getItems().setAll(restaurants);
            foodCategoryListView.getItems().setAll(foodCategories);
            decorateRestList();
            decorateFoodCategoryList();

            restListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    System.out.println("Selected: " + newVal.restaurant_name);
                }
            });
            foodCategoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    System.out.println("Selected: " + newVal.getCatagory_name());
                }
            });
        }else {
            restListView.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            foodCategoryListView.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        applyGlassStyle(pane1, 30, 255, 0.5, 255, 0.3, 5);
        applyGlassStyle(accountinfopane, 0, 255, 0.5, 0, 0.3, 1);
        applyGlassStyle(pane2, 30, 0, 0.5, 0, 0.15, 5);
        applyGlassStyle(accountinfobutton, 10, 255, 0.25, 255, 0.3, 2);

    }
    public void onHoverAccountInfoButton(){
        System.out.println("Mouse is in");
        applyGlassStyle(accountinfobutton, 10, 255, 0.25, 255, 0.6, 2);
    }
    public void onMouseLeaveAccountInfoButton(){
        System.out.println("Mouse is out");
        applyGlassStyle(accountinfobutton, 10, 255, 0.25, 255, 0.3, 2);
    }
    public void onAccountInfoButoonClick(){
        System.out.println("Mouse click");
        accountInfoOpen = true;
        Accountinfoimgview.setX(300);
        accountinfopane.setLayoutX(0);
    }
    @FXML
    private void decorateRestList(){
        restListView.setCellFactory(listView -> new ListCell<Restaurant>() {
            private final ImageView logoView = new ImageView();
            private int logoCornerRadius = 20;
            private final Label nameLabel = new Label();
            private final Label ratingLabel = new Label();
            private final HBox content = new HBox(10); // spacing between items
            private final Line separator = new Line();

            {
                logoView.setFitWidth(40);
                logoView.setFitHeight(40);
                logoView.setPreserveRatio(true);
                Rectangle clip = new Rectangle(40, 40);
                clip.setArcWidth(logoCornerRadius);
                clip.setArcHeight(logoCornerRadius);
                logoView.setClip(clip);

                nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
                ratingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                nameLabel.setPadding(new Insets(0, 0, 0,30));
                HBox.setHgrow(nameLabel, Priority.ALWAYS);
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS); // this makes spacer push rating to right

                content.setAlignment(Pos.CENTER_LEFT);
                content.getChildren().addAll(logoView, nameLabel, spacer, ratingLabel);
                content.setPadding(new Insets(5, 50, 10, 20)); // Padding around cell content
                content.setMaxWidth(470);
                content.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

                separator.setStroke(Color.rgb(255, 255, 255, 0.1));
                separator.setStrokeWidth(1);
                separator.endXProperty().bind(content.widthProperty());

                VBox wrapper = new VBox(content, separator);
                wrapper.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

                setGraphic(wrapper);
                setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            }

            @Override
            protected void updateItem(Restaurant restaurant, boolean empty) {
                super.updateItem(restaurant, empty);
                if (empty || restaurant == null) {
                    setGraphic(null);
                } else {
                    logoView.setImage(new Image("file:" + "C:/Users/MArib/OneDrive/Desktop/image.png"));
                    nameLabel.setText(restaurant.restaurant_name);
                    ratingLabel.setText(String.valueOf(restaurant.rate.rating));
                    setGraphic(new VBox(content, separator));
                }
            }
        });
        BackgroundFill fill = new BackgroundFill(
                Color.rgb(0, 0, 0, 0),
                new CornerRadii(0),
                Insets.EMPTY
        );
        restListView.setBackground(new Background(fill));
    }
    public void putTextinPane(Pane pane, String txt, int x, int y){
        Text title = new Text(txt);
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-BlackItalic.otf"), 50));
        //title.setFill(Color.rgb(150, 68, 194, 1));
        title.setFill(Color.rgb(245, 71, 123, 1));
        title.setStroke(Color.rgb(255, 255, 255, 0.8));
        title.setStrokeWidth(2);

        title.setEffect(new DropShadow(50, Color.rgb(255, 196, 214, 1)));

        title.setLayoutX(x);
        title.setLayoutY(y);

        pane.getChildren().add(title);
    }

    public void loadFoodCategoryclasses(){
        foodCategories.add(new FoodCategory("Drinks", "/FoodCategoryLogo/1.jpg"));
        foodCategories.add(new FoodCategory("Fast Food", "/FoodCategoryLogo/2.jpg"));
        foodCategories.add(new FoodCategory("Deserts", "/FoodCategoryLogo/3.jpg"));
        foodCategories.add(new FoodCategory("Fruits", "/FoodCategoryLogo/4.jpg"));
        foodCategories.add(new FoodCategory("Heavy", "/FoodCategoryLogo/5.jpg"));
    }
    public void decorateFoodCategoryList(){
        foodCategoryListView.setCellFactory(lv -> new ListCell<FoodCategory>() {
            private VBox content;
            private ImageView iconView;
            private Label nameLabel;

            {
                iconView = new ImageView();
                iconView.setFitWidth(60);
                iconView.setFitHeight(60);
                iconView.setPreserveRatio(true);

                Rectangle background = new Rectangle(70, 70);
                background.setFill(Color.WHITE);
                background.setArcWidth(20);
                background.setArcHeight(20);
                Rectangle shadow = new Rectangle(70, 70);
                shadow.setFill(Color.BLACK);
                shadow.setArcWidth(20);
                shadow.setArcHeight(20);
                shadow.setEffect(new GaussianBlur(10));
                shadow.setOpacity(0.15);

                StackPane iconPane = new StackPane(shadow, background, iconView);
                iconPane.setPadding(new Insets(0, 5, 0, 5));

                nameLabel = new Label();
                nameLabel.setTextFill(Color.BLACK); // or themed color
                nameLabel.setFont(Font.font(12));
                nameLabel.setAlignment(Pos.CENTER);
                nameLabel.setWrapText(true);

                content = new VBox(5, iconPane, nameLabel);
                content.setAlignment(Pos.TOP_CENTER);
                content.setPadding(new Insets(0));
            }

            @Override
            protected void updateItem(FoodCategory item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    iconView.setImage(new Image(getClass().getResource(item.getImg_path()).toExternalForm()));
                    nameLabel.setText(item.getCatagory_name());
                    setGraphic(content);
                    setBackground(null); // so it blends with transparent list background
                    setPrefHeight(60);
                }
            }
        });
        foodCategoryListView.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    }

}
