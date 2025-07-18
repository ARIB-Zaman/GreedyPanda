package com.example.javafx_food_project.Communication;

import java.io.Serializable;

public class Data implements Serializable,Cloneable{
    
    public String message;           
    
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();
    }  
}
  