package com.example.javafx_food_project;

import com.example.javafx_food_project.misc_classes.FoodCategory;
import com.example.javafx_food_project.misc_classes.FoodItem;
import com.example.javafx_food_project.misc_classes.Restaurant;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.javafx_food_project.LoginPage.*;
import static com.example.javafx_food_project.LoginPage_Controller.*;
import static java.lang.Math.ceil;
import static java.lang.Math.max;

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
    private Button cartButton;
    @FXML
    private Button orderButton;
    @FXML
    private ImageView Accountinfoimgview;
    @FXML
    private ListView<Restaurant> restListView;
    @FXML
    private ListView<FoodCategory> foodCategoryListView;
    @FXML
    private Pane foodlistpane;
    @FXML
    private ScrollPane foodscrollpane;
    @FXML
    private GridPane foodgridpane;
    @FXML
    private Pane foodlistanimatepane;
    @FXML
    private Pane referencepane;
    @FXML
    private ImageView foodlistblutimgview;
    @FXML
    private Pane referenceaccpane;

    private int currentFrame = 32;
    private Stage stage;
    private Scene scene;
    List<Image> frames = new ArrayList<>();
    private boolean accountInfoOpen = false;
    private boolean foodListGridOpen = false;
    private boolean cartViewOpen = false;
    List<FoodCategory> foodCategories = new ArrayList<>();
    List<FoodItem> foodItemList = new ArrayList<>();
    public static List<FoodItem> cart = new ArrayList<>();

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
                orderButton.setVisible(cartViewOpen);

            } catch (Exception e) {}
                Image blr = cropAndBlurImage(frames.get(currentFrame),referenceaccpane, 25);
                Accountinfoimgview.setImage(blr);
                foodlistblutimgview.setImage(cropAndBlurImage2(frames.get(currentFrame), referencepane, 20));

            currentFrame = ((currentFrame+1)%49);
        }
    }

    @FXML
    public void initialize(){
        Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Regular.otf"), 12);
        orderButton.setVisible(false);
        //grabYourMunch.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 50));

        Image image = new Image(getClass().getResourceAsStream("/trframes1/tr0032.jpg"));
        if(currentScene.equals("HomepageCustomer")) {
            bgimageview2.setImage(image);
            restaurants = cusClient.getRestaurantList();
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
            stylizeOrderButton();

            restListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    System.out.println("Selected: " + newVal.restaurant_name + " id: " + newVal.restaurantUniqeID);
                    List<FoodItem> f = cusClient.getFoodListByRestID(newVal.restaurantUniqeID);

                    if(!foodListGridOpen){

                        applyGlassStyle(foodlistpane, 30, 255, 0.63, 255, 0.3, 5);
                        stylizeFoodListPanes();

                        TranslateTransition fpane = new TranslateTransition(Duration.millis(200), foodlistanimatepane);
                        fpane.setByY(164-750);
                        fpane.setOnFinished(event -> {
                            foodlistblutimgview.setVisible(true);
                            populateFoodGrid(f, false);
                        });
                        fpane.play();
                        foodListGridOpen = true;

                    }
                    else populateFoodGrid(f, false);
                    cartViewOpen = false;
                    applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.3, 2);
                }
            });
            foodlistblutimgview.setVisible(false);
            Accountinfoimgview.setVisible(false);
            foodCategoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    System.out.println("Selected: " + newVal.getCatagory_name());
                    List<FoodItem> f = cusClient.getFoodListByCategory(newVal.getCatagory_name());

                    if(!foodListGridOpen){
                        applyGlassStyle(foodlistpane, 30, 255, 0.63, 255, 0.3, 5);
                        stylizeFoodListPanes();

                        TranslateTransition fpane = new TranslateTransition(Duration.millis(100), foodlistanimatepane);
                        fpane.setByY(164-750);
                        fpane.setOnFinished(event -> {
                            foodlistblutimgview.setVisible(true);
                            populateFoodGrid(f, false);

                        });
                        fpane.play();
                        foodListGridOpen = true;

                    }
                    else populateFoodGrid(f, false);
                    cartViewOpen = false;
                    applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.3, 2);
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
        applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.3, 2);
        //foodgridpane.setOpacity(0);
        BackgroundFill fill = new BackgroundFill(
                Color.rgb(0,0,0,0),
                new CornerRadii(30),
                Insets.EMPTY
        );
        foodscrollpane.setBackground(new Background(fill));
    }
    public void onHoverAccountInfoButton(){
        System.out.println("Mouse is in");
        applyGlassStyle(accountinfobutton, 10, 255, 0.25, 255, 0.6, 2);
    }
    public void onMouseLeaveAccountInfoButton(){
        System.out.println("Mouse is out");
        applyGlassStyle(accountinfobutton, 10, 255, 0.25, 255, 0.3, 2);
    }
    public void onHoverCartButton(){
        System.out.println("Mouse is in");
        if(!cartViewOpen) applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.6, 2);
    }
    public void onMouseLeaveCartButton(){
        System.out.println("Mouse is out");
        if(!cartViewOpen) applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.3, 2);
    }
    public void onAccountInfoButoonClick(){
        System.out.println("Mouse click");
        if(!accountInfoOpen){

        accountInfoOpen = true;
        TranslateTransition aipane = new TranslateTransition(Duration.millis(100), accountinfopane);
        aipane.setByX(300);
        aipane.setOnFinished(event -> {
            Accountinfoimgview.setVisible(true);
        });
        aipane.play();
        //Accountinfoimgview.setX(300);
        //accountinfopane.setLayoutX(0);
        }
    }

    public void onCartButtonClick(){
        applyGlassStyle(cartButton, 10, 255, 0.7, 255, 0.6, 2);

        if(!foodListGridOpen){
            applyGlassStyle(foodlistpane, 30, 255, 0.63, 255, 0.3, 5);
            stylizeFoodListPanes();

            TranslateTransition fpane = new TranslateTransition(Duration.millis(200), foodlistanimatepane);
            fpane.setByY(164-750);
            fpane.setOnFinished(e -> {
                foodlistblutimgview.setVisible(true);
            });
            fpane.play();
            foodListGridOpen = true;
        }
        cartViewOpen = true;
        populateFoodGrid(cart, true);
    }

    public void onOrderButtonClick(ActionEvent event) throws IOException {
        if(cart.size()>0){
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/OrderPage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            currentScene = "OrderPage";
        }

    }

    public void closeAllTabs(){
        if(accountInfoOpen){
            accountInfoOpen = false;
            Accountinfoimgview.setVisible(false);
            TranslateTransition aipane = new TranslateTransition(Duration.millis(100), accountinfopane);
            aipane.setByX(-300);
            aipane.play();
            //Accountinfoimgview.setX(-300);
            //accountinfopane.setLayoutX(-300);
        }
        if(foodListGridOpen){
            foodListGridOpen = false;
            foodlistblutimgview.setVisible(false);
            TranslateTransition fpane = new TranslateTransition(Duration.millis(100), foodlistanimatepane);
            fpane.setByY(750-164);

            fpane.play();
        }
        cartViewOpen = false;
        applyGlassStyle(cartButton, 10, 255, 0.25, 255, 0.3, 2);

        System.out.println("Cancel click");
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
        title.setFill(Color.rgb(255, 15, 87, 1));
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


                content.setOnMouseExited(e ->{
                });
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
                    //setPrefHeight(60);
                    content.setPrefHeight(60);
                }


            }
        });
        foodCategoryListView.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    }
    private VBox createFoodCard(FoodItem food, double x, double y){
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(x, y);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 2);");

        // Image
        Image image= new Image(getClass().getResourceAsStream("/foodItemImgs/001.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        // Name & Price
        Label nameLabel = new Label(food.name);
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label priceLabel = new Label("৳ " + food.price);

        // Button
        Button addButton = new Button("Add to Cart");
        addButton.setStyle(
                "-fx-background-color: #ff2160;" +
                "-fx-background-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 0;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 2);");
        addButton.setOnAction(e -> {
            System.out.println(food.name + " added to cart!");
            cart.add(food);
        });

        card.getChildren().addAll(imageView, nameLabel, priceLabel, addButton);
        card.setMaxHeight(y);
        card.setMaxWidth(x);
        card.setMinWidth(x);

        return card;
    }
    private VBox createFoodCardforCart(FoodItem food, double x, double y){
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(x, y);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 2);");

        // Image
        Image image= new Image(getClass().getResourceAsStream("/foodItemImgs/001.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        // Name & Price
        Label nameLabel = new Label(food.name);
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label priceLabel = new Label("৳ " + food.price);

        // Button
        Button addButton = new Button("Remove");
        addButton.setStyle(
                "-fx-background-color: #ff2160;" +
                "-fx-background-radius: 10;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #ffffff;" +
                        "-fx-border-width: 0;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0.3, 0, 2);");
        addButton.setOnAction(e -> {
            System.out.println(food.name + " removed from cart!");
            cart.remove(food);
            populateFoodGrid(cart, true);
        });

        card.getChildren().addAll(imageView, nameLabel, priceLabel, addButton);
        card.setMaxHeight(y);
        card.setMaxWidth(x);
        card.setMinWidth(x);

        return card;
    }
    public void populateFoodGrid(List<FoodItem> foodList, boolean isCart) {
        foodgridpane.getChildren().clear();
        int maxColumns = 3;
        if(foodList.size()<3) maxColumns = foodList.size();
        int column = 0;
        int row = 0;
        int gap = 15;
        double x = (foodgridpane.getWidth() - (maxColumns-1)*gap - 20)/(double) maxColumns;

        double y = 260;
        foodgridpane.setPadding(new Insets(10));
        double prefHeight = ceil(foodList.size()/(double)maxColumns) * y + (ceil(foodList.size()/(double)maxColumns)-1)*gap;
        if(ceil(foodList.size()/(double)maxColumns)<3){
            prefHeight = 3*y + 2*gap;
        }
        foodgridpane.setPrefHeight(prefHeight);

        foodgridpane.setVgap(gap);
        foodgridpane.setHgap(gap);
        for (FoodItem food : foodList) {
            VBox card = new VBox();
            if(!isCart) card = createFoodCard(food, x, y);
            else card = createFoodCardforCart(food, x, y);

            foodgridpane.add(card, column, row);

            //System.out.println("creating card: " + food.name);
            column++;
            if (column == maxColumns) {
                column = 0;
                row++;
            }
        }
    }
    public void stylizeFoodListPanes(){
        foodscrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        foodscrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Platform.runLater(() -> {
            Node viewport = foodscrollpane.lookup(".viewport");
            if (viewport != null) {
                viewport.setStyle("-fx-background-color: transparent;");
            }
        });
    }

    public void stylizeOrderButton(){
        orderButton.setText("Order Now");
        orderButton.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 25));
        orderButton.setTextFill(Color.WHITE);

// Gradient background
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#FF416C")), new Stop(1, Color.web("#FF4B2B")) };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        orderButton.setBackground(new Background(new BackgroundFill(
                gradient,
                new CornerRadii(30), // pill shape
                Insets.EMPTY
        )));

// Thin white stroke
        orderButton.setBorder(new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.SOLID,
                new CornerRadii(30),
                new BorderWidths(1)
        )));

// Padding for sizing
        orderButton.setPadding(new Insets(12, 30, 12, 30));

// Drop shadow for depth
        DropShadow shadow = new DropShadow(10, Color.rgb(0, 0, 0, 0.25));
        orderButton.setEffect(shadow);

// Hover effect: brighten the gradient a bit
        orderButton.setOnMouseEntered(e -> {
            Stop[] hoverStops = new Stop[] {
                    new Stop(0, Color.web("#FA5425")),
                    new Stop(1, Color.web("#FA2550"))
            };
            LinearGradient hoverGradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, hoverStops);
            orderButton.setBackground(new Background(new BackgroundFill(
                    hoverGradient, new CornerRadii(30), Insets.EMPTY
            )));
        });

        orderButton.setOnMouseExited(e -> {
            orderButton.setBackground(new Background(new BackgroundFill(
                    gradient, new CornerRadii(30), Insets.EMPTY
            )));
        });


    }

}
