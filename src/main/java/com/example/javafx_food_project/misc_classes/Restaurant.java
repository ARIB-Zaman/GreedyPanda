package com.example.javafx_food_project.misc_classes;
import com.example.javafx_food_project.user_classes.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Restaurant {
    String restaurant_name;
    int restaurantUniqeID;
    RestaurantManager restaurantManager;
    String restaurant_location;
    Rating rate;
    LocalTime openingTime;
    LocalTime closingTime;
    String SalesHistory;

    public Restaurant(String restaurant_name, RestaurantManager restaurantManager){
        this.restaurant_name = restaurant_name;
        this.restaurantManager = restaurantManager;
        rate = new Rating();
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
