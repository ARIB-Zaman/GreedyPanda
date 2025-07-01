module com.example.javafx_food_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafx_food_project to javafx.fxml;
    exports com.example.javafx_food_project;
    exports com.example.javafx_food_project.user_classes;
    opens com.example.javafx_food_project.user_classes to javafx.fxml;
}