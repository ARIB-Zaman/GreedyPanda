package com.example.javafx_food_project;

import com.example.javafx_food_project.misc_classes.FoodCategory;
import com.example.javafx_food_project.misc_classes.FoodItem;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static com.example.javafx_food_project.HomepageCustomerController.cart;
import static com.example.javafx_food_project.LoginPage.restaurants;

public class OrderPageController {
    @FXML
    private ListView<FoodItem> cartListView;



    @FXML
    public void initialize(){
        decorateCartList();
        cartListView.getItems().setAll(cart);

    }

    public void decorateCartList(){
        cartListView.setCellFactory(lv -> new ListCell<FoodItem>() {
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


                content.setOnMouseExited(e ->{
                });
            }

            @Override
            protected void updateItem(FoodItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    iconView.setImage(new Image(getClass().getResource("/foodItemImgs/001.jpg").toExternalForm()));
                    nameLabel.setText(item.name);
                    setGraphic(content);
                    setBackground(null); // so it blends with transparent list background
                    //setPrefHeight(60);
                    content.setPrefHeight(60);
                }


            }
        });
        //cartListView.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    }
}
