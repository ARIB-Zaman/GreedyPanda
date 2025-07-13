package com.example.javafx_food_project;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.javafx_food_project.LoginPage.currentScene;


public class LoginPage_Controller {
    private Stage stage;
    private Scene scene;
    private Parent parent;
    private List<Image> frames = new ArrayList<>();
    private int currentFrame = 1;

    @FXML
    private ImageView bgimageview;
    @FXML
    private Button submitbutton;
    @FXML
    private Pane glasspane;
    @FXML
    private ImageView glassview;
    @FXML
    private Label usertype;
    @FXML
    private TextField user_name_field;
    @FXML
    private Label password_lable;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label error_msg_box;

    private void preloadFrames() {
        for (int i = 1; i <= 50; i++) {
            String frameName = String.format("/bgframes1/bg%04d.jpg", i);
            try {
                Image image = new Image(getClass().getResource(frameName).toExternalForm());
                frames.add(image);
            } catch (Exception e) {
                System.err.println("Could not preload: " + frameName);
            }
        }
    }
    private void updateFrame() {
        if (!frames.isEmpty() && currentScene.equals("LoginPage")) {
            try{

            bgimageview.setImage(frames.get(currentFrame));
            Image blr = cropAndBlurImage(frames.get(currentFrame), glasspane, 25);
            glassview.setImage(blr);
            } catch (Exception e) {}

            currentFrame = ((currentFrame+1)%50)+1;
        }
    }
    public void initialize(){
        Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 12);
        preloadFrames();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(42), e -> updateFrame())); // 24 FPS
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        try {
            applyGlassStyle(glasspane, 30, 0, 0.1, 255, 0.2, 10);
            applyGlassStyle(submitbutton, 30, 0, 0.1, 0, 0.1, 3);
            boundImageInAPane(glassview, glasspane, 30);
        } catch (Exception e) {}
        //user_name_field.setOpacity(0.4);
        //passwordField.setOpacity(0.4);
        BackgroundFill fill = new BackgroundFill(
                Color.rgb(255, 255, 255, 0.3), // semi-transparent white
                new CornerRadii(15),
                Insets.EMPTY
        );
        user_name_field.setBackground(new Background(fill));
        passwordField.setBackground(new Background(fill));
        usertype.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 30));
        password_lable.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/mocoSans-Black.otf"), 30));
    }
    @FXML
    protected void onSubmitButtonClick(ActionEvent event) throws IOException {
        String userName = user_name_field.getText();
        String password = passwordField.getText();
        if(HandleDatabase.verifyCustomer(userName, password)) switchToHomepageCustomer(event);
        else  error_msg_box.setText("Incorrect username or password");
    }
    @FXML
    public void onMouseExitSubmitButton(){
        applyGlassStyle(submitbutton, 30, 0, 0.1, 0, 0.1, 3);
    }
    @FXML
    public void onMouseEnterSubmitButton(){
        applyGlassStyle(submitbutton, 30, 255, 0.1, 255, 0.1, 3);
    }

    public void switchToHomepageCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafx_food_project/AfterLoginTransition.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        currentScene = "AfterLoginTransition";
    }
    public static Image cropAndBlurImage(
            Image sourceImage,
            Pane targetPane,
            double blurRadius
    ) {

        double cropX = targetPane.getLayoutX();
        double cropY = targetPane.getLayoutY();
        double cropWidth = targetPane.getWidth();
        double cropHeight = targetPane.getHeight();
        // 1. Crop the region using PixelReader
        PixelReader reader = sourceImage.getPixelReader();
        if (reader == null) return null;

        WritableImage cropped = new WritableImage(reader,
                (int) cropX, (int) cropY,
                (int) cropWidth, (int) cropHeight);

        // 2. Wrap in ImageView and apply GaussianBlur
        ImageView imageView = new ImageView(cropped);
        imageView.setFitWidth(cropWidth);
        imageView.setFitHeight(cropHeight);
        imageView.setPreserveRatio(false);
        imageView.setEffect(new GaussianBlur(blurRadius));

        // 3. Snapshot the blurred result and return
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        params.setViewport(new Rectangle2D(0, 0, cropWidth, cropHeight));

        WritableImage blurredImage = new WritableImage((int) cropWidth, (int) cropHeight);
        imageView.snapshot(params, blurredImage);

        return blurredImage;
    }
    public static void applyGlassStyle(
            Pane glassPane,
            double cornerRadius,
            int glassColorGray,
            double glassOpacity,
            int strokeColorGray,
            double strokeOpacity,
            int strokeWidth
    ) {
        // Apply background
        BackgroundFill fill = new BackgroundFill(
                Color.rgb(glassColorGray, glassColorGray, glassColorGray, glassOpacity),
                new CornerRadii(cornerRadius),
                Insets.EMPTY
        );
        glassPane.setBackground(new Background(fill));

        // Create stroke rectangle
        Rectangle borderRect = new Rectangle();
        borderRect.setArcWidth((cornerRadius - (strokeWidth / 2.0)) * 2);
        borderRect.setArcHeight((cornerRadius - (strokeWidth / 2.0)) * 2);
        borderRect.setFill(Color.TRANSPARENT);
        borderRect.setStroke(Color.rgb(strokeColorGray, strokeColorGray, strokeColorGray, strokeOpacity));
        borderRect.setStrokeWidth(strokeWidth);
        borderRect.widthProperty().bind(glassPane.widthProperty().subtract(strokeWidth));
        borderRect.heightProperty().bind(glassPane.heightProperty().subtract(strokeWidth));
        borderRect.setMouseTransparent(true);
        borderRect.setX(glassPane.getLayoutX()+ (strokeWidth / 2.0));
        borderRect.setY(glassPane.getLayoutY()+ (strokeWidth / 2.0));

        // Add to parent
        if (glassPane.getParent() instanceof Pane parent) {
            parent.getChildren().add(borderRect);
            borderRect.toFront();
        }
    }
    public static void boundImageInAPane(ImageView imageView, Pane pane, int cornerRadius){
        Rectangle clip = new Rectangle();
        clip.setArcWidth(cornerRadius * 2);
        clip.setArcHeight(cornerRadius * 2);
        clip.widthProperty().bind(pane.widthProperty());
        clip.heightProperty().bind(pane.heightProperty());
        imageView.setClip(clip);
        clip.setMouseTransparent(true);
    }
    public static void applyGlassStyle(
            Button glassPane,
            double cornerRadius,
            int glassColorGray,
            double glassOpacity,
            int strokeColorGray,
            double strokeOpacity,
            int strokeWidth
    ) {
        // Apply background
        BackgroundFill fill = new BackgroundFill(
                Color.rgb(glassColorGray, glassColorGray, glassColorGray, glassOpacity),
                new CornerRadii(cornerRadius),
                Insets.EMPTY
        );
        glassPane.setBackground(new Background(fill));
        if (glassPane.getParent() instanceof Pane parent) {
            // Remove existing border only for this button
            parent.getChildren().removeIf(node -> node.getUserData() instanceof Node && node.getUserData() == glassPane);
        }
        // Create stroke rectangle
        Rectangle borderRect = new Rectangle();
        borderRect.setArcWidth((cornerRadius - (strokeWidth / 2.0)) * 2);
        borderRect.setArcHeight((cornerRadius - (strokeWidth / 2.0)) * 2);
        borderRect.setFill(Color.TRANSPARENT);
        borderRect.setStroke(Color.rgb(strokeColorGray, strokeColorGray, strokeColorGray, strokeOpacity));
        borderRect.setStrokeWidth(strokeWidth);
        borderRect.widthProperty().bind(glassPane.widthProperty().subtract(strokeWidth));
        borderRect.heightProperty().bind(glassPane.heightProperty().subtract(strokeWidth));
        borderRect.setMouseTransparent(true);
        borderRect.setX(glassPane.getLayoutX()+ (strokeWidth / 2.0));
        borderRect.setY(glassPane.getLayoutY()+ (strokeWidth / 2.0));
        borderRect.setUserData(glassPane);


        Circle light = new Circle(5);
        light.setFill(Color.rgb(255,255,255,0.7));
        light.setEffect(new GaussianBlur(30));
        light.setMouseTransparent(true);



        glassPane.setOnMouseMoved(event -> {
            Bounds bounds = glassPane.localToParent(glassPane.getBoundsInLocal());
            double mouseX = bounds.getMinX() + event.getX();
            double mouseY = bounds.getMinY() + event.getY();
            light.setLayoutX(mouseX);
            light.setLayoutY(mouseY);
        });

        // Add to parent
        if (glassPane.getParent() instanceof Pane parent) {
            parent.getChildren().add(borderRect);
            parent.getChildren().add(light);
            borderRect.toFront();
            light.toFront();
        }

        EventHandler<MouseEvent> originalHandler = (EventHandler<MouseEvent>) glassPane.getOnMouseExited();
        glassPane.setOnMouseExited(e -> {
            light.setVisible(false); // Your glow logic
            if (originalHandler != null) {
                originalHandler.handle(e); // Call previous logic too
            }
        });
    }
}