package com.example.javafx_food_project.misc_classes;

import java.io.Serializable;

public class FoodItem implements Serializable {
    public String name;
    public int foodID;
    public double price;
    public Rating rate;
    public String imgPath;

    public FoodItem(String name, String imgPath){
        this.name = name;
        this.imgPath = imgPath;
        rate = new Rating();
    }
    public FoodItem(String name, String imgPath,double price, double rate){
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.rate = new Rating(1, rate);
    }
    public FoodItem(String name, String imgPath,double price, int totalRating, double rate){
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.rate = new Rating(totalRating, rate);
    }

}
