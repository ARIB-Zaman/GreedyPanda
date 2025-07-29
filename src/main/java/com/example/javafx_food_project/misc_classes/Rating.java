package com.example.javafx_food_project.misc_classes;

import java.io.Serializable;

public class Rating implements Serializable {
    public int totalRating;
    public double rating;
    public Rating(){
        totalRating = 0;
        rating = 0;
    }
    public Rating(int t, double r){
        totalRating = t;
        rating = r;
    }
    public double addRating(double rate){
        rating = (totalRating*rating + rate)/(++totalRating);
        return rating;
    }
    public void clearALL(){
        rating = 0;
        totalRating = 0;
    }
}
