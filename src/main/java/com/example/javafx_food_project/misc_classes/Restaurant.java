package com.example.javafx_food_project.misc_classes;
import com.example.javafx_food_project.user_classes.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Restaurant implements Serializable {
    public String restaurant_name;
    public int restaurantUniqeID;
    public RestaurantManager restaurantManager;
    public String restaurant_location;
    public Rating rate;
    public LocalTime openingTime;
    public LocalTime closingTime;
    public String SalesHistory;

    public Restaurant(String restaurant_name, RestaurantManager restaurantManager){
        this.restaurant_name = restaurant_name;
        this.restaurantManager = restaurantManager;
        rate = new Rating();
    }
    public Restaurant(String restaurant_name, RestaurantManager restaurantManager, int totalRating, double rating){
        this.restaurant_name = restaurant_name;
        this.restaurantManager = restaurantManager;
        rate = new Rating(totalRating, rating);
    }
    public Restaurant(String restaurant_name, int totalRating, double rating, String openingTime, String closingTime){
        this.restaurant_name = restaurant_name;
        rate = new Rating(totalRating, rating);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        this.openingTime =  LocalTime.parse(openingTime, formatter);
        this.closingTime =  LocalTime.parse(closingTime, formatter);
    }
    public Restaurant(String restaurant_name,int id, int totalRating, double rating, String openingTime, String closingTime){
        this.restaurant_name = restaurant_name;
        this.restaurantUniqeID = id;
        rate = new Rating(totalRating, rating);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.US);
        this.openingTime =  LocalTime.parse(openingTime, formatter);
        this.closingTime =  LocalTime.parse(closingTime, formatter);
    }

    public void setOpenClosetime(LocalTime opening, LocalTime closing){
        this.openingTime = opening;
        this.closingTime = closing;
    }
    public void setRestaurant_location(String loc){
        this.restaurant_location = loc;
    }
    public boolean isOpen(LocalTime inputTime){
        if(openingTime == null || closingTime == null) return false;
        if(closingTime.isAfter(openingTime)){
            return closingTime.isAfter(inputTime) && openingTime.isBefore(inputTime);
        }
        else if(closingTime.isBefore(openingTime)){
            return inputTime.isAfter(openingTime) || inputTime.isBefore(closingTime);
        }
        return true;  //restaurant is open 24/7
    }
    public String returnNiceTime(LocalTime inputTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return inputTime.format(formatter);
    }


}
