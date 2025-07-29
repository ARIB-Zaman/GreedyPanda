
package com.example.javafx_food_project.Communication;

import com.example.javafx_food_project.Communication.NetworkConnection;
import com.example.javafx_food_project.Communication.Reader;
import com.example.javafx_food_project.Communication.Writer;
import com.example.javafx_food_project.HandleDatabase;
import com.example.javafx_food_project.misc_classes.DataPacket;
import com.example.javafx_food_project.misc_classes.FoodItem;
import com.example.javafx_food_project.misc_classes.Restaurant;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ClientManager {
    Socket socket;
    public NetworkConnection nc;
    public boolean verified = false;
    public ClientManager() throws IOException {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("www.google.com", 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client Started--- ");
        System.out.println(socket.getLocalAddress().getHostAddress());
        nc = new NetworkConnection(socket.getLocalAddress().getHostAddress(),12345);
        System.out.println("Connected!");
        DataPacket setUserType = new DataPacket();
        setUserType.usertype = "customer";
        nc.write(setUserType.clone());
    }

    public boolean verifyLogin(String cname, String cpass){
        DataPacket loginInfo = new DataPacket();
        loginInfo.cusLoginPacket(cname, cpass);
        nc.write(loginInfo.clone());

        Object obj = nc.read();
        loginInfo = (DataPacket) obj;
        System.out.println("Server response: "+loginInfo.command);
        if(loginInfo.command.equalsIgnoreCase("verified")){
            verified = true;
            return true;
        }
        return false;
    }

    public boolean validForSignup(String cname, String email, String cpass){
        DataPacket signupInfo = new DataPacket();
        signupInfo.cusSignupPacket(cname, email, cpass);
        signupInfo.setCommand("checkValidityForSignup");
        nc.write(signupInfo.clone());

        Object obj = nc.read();
        signupInfo = (DataPacket) obj;
        System.out.println("Server response: "+signupInfo.command);
        if(signupInfo.command.equalsIgnoreCase("isvalid")){
            return true;
        }
        return false;
    }
    public void signupNewCustomer(String cname, String email, String cpass){
        DataPacket signupInfo = new DataPacket();
        signupInfo.cusSignupPacket(cname, email, cpass);
        nc.write(signupInfo.clone());
    }
    public List<Restaurant> getRestaurantList(){
        DataPacket d = new DataPacket();
        d.setCommand("getrestaurantlist");
        nc.write(d.clone());

        Object obj = nc.read();
        d = (DataPacket) obj;

        return d.restaurantList;
    }

    public List<FoodItem> getFoodListByCategory(String category){
        List<FoodItem> foodItemList = new ArrayList<>();
        DataPacket d = new DataPacket();
        d.setCommand("askFoodListbyCategory");
        d.customerEmail = category;
        nc.write(d.clone());

        Object obj = nc.read();
        d = (DataPacket) obj;
        foodItemList = d.foodItemList;

        return foodItemList;
    }
    public List<FoodItem> getFoodListByRestID(int id){
        List<FoodItem> foodItemList = new ArrayList<>();
        DataPacket d = new DataPacket();
        d.setCommand("askFoodListbyRestID");
        d.restID = id;
        nc.write(d.clone());

        Object obj = nc.read();
        d = (DataPacket) obj;
        foodItemList = d.foodItemList;

        return foodItemList;
    }

}
