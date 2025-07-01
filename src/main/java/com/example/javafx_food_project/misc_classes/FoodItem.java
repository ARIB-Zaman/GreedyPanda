package com.example.javafx_food_project.misc_classes;

public abstract class FoodItem {
    String name;
    int foodID;
    int calories_per_something;
    Rating rate;

    public FoodItem(String name){
        this.name = name;
        rate = new Rating();
    }

}
