package com.example.javafx_food_project.misc_classes;
import com.example.javafx_food_project.misc_classes.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataPacket implements Serializable,Cloneable{
    public String command;
    public String usertype;
    public String customerName;
    public String customerPass;
    public String customerEmail;
    public List<Restaurant> restaurantList;
    public int restID;
    public  List<FoodItem> foodItemList;

    public DataPacket(){}

    public void cusLoginPacket(String cname, String cpass){
        usertype = "customer";
        command = "verifylogin";
        customerName = cname;
        customerPass = cpass;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void cusSignupPacket(String cname, String email, String cpass){
        usertype = "customer";
        command = "cussignup";
        customerName = cname;
        customerEmail = email;
        customerPass = cpass;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        command = "sendrestaurantlist";
        this.restaurantList = restaurantList;
    }

    public void sendFoodListbyCategory(List<FoodItem> foodItemList){
        command = "sendFoodListbyCategory";
        this.foodItemList = foodItemList;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    @Override
    public DataPacket clone() {
        try {
            DataPacket clone = (DataPacket) super.clone();

            if (this.restaurantList != null) {
                clone.restaurantList = new ArrayList<>(this.restaurantList); // shallow copy of list
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
