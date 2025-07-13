package com.example.javafx_food_project.misc_classes;

import java.util.ArrayList;
import java.util.List;

public class FoodCategory {
    //drinks, fastfood, heavy, deserts, fruits/farm fresh.
    String category_name;
    String img_path;
    List<String> foods = new ArrayList<>();
    public FoodCategory(String catagory_name, String img_path){
        this.category_name = catagory_name;
        this.img_path = img_path;
    }

    public String getImg_path() {
        return img_path;
    }
    public String getCatagory_name() {
        return category_name;
    }
}
