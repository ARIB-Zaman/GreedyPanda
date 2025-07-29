package com.example.javafx_food_project.Communication;

import com.example.javafx_food_project.HandleDatabase;
import com.example.javafx_food_project.misc_classes.DataPacket;
import com.example.javafx_food_project.misc_classes.Restaurant;

import java.util.HashMap;
import java.util.Map;


public class ExecuteCommands implements Runnable {

    String username;
    NetworkConnection nc;
    HashMap<String, Information> clientList;

    public ExecuteCommands(String user, NetworkConnection netConnection, HashMap<String, Information> cList) {
        username = user;
        nc = netConnection;
        clientList = cList;
        System.out.println("Execution stage 1");
    }

    @Override
    public void run() {
        System.out.println("Execution stage 2");
        while (true) {
            Object obj = nc.read();
            DataPacket dataObj=(DataPacket) obj;
            String command=dataObj.command;
            System.out.println("Received command: " + command);

            if (command.equalsIgnoreCase("verifylogin")) {
                System.out.println("Executing: " + command);
                if(HandleDatabase.verifyCustomer(dataObj.customerName, dataObj.customerPass)) dataObj.setCommand("verified");
                else dataObj.setCommand("unverified");
                nc.write(dataObj.clone());
            }

            if(command.equalsIgnoreCase("checkvalidityforsignup")){
                System.out.println("Executing: " + command);
                if(HandleDatabase.validCusForSignup(dataObj.customerName, dataObj.customerEmail)){
                    dataObj.setCommand("isValid");
                    System.out.println("Customer "+dataObj.customerName+" valid for signup");
                }else{
                    dataObj.setCommand("notValid");
                    System.out.println("Customer "+dataObj.customerName+" is not valid for signup");
                }
                nc.write(dataObj.clone());
            }

            if (command.equalsIgnoreCase("cussignup")){
                System.out.println("Executing: " + command);
                if(HandleDatabase.validCusForSignup(dataObj.customerName, dataObj.customerEmail)){
                    HandleDatabase.addNewCustomer(dataObj.customerName, dataObj.customerEmail, dataObj.customerPass);
                    System.out.println("ADDed new Customer: "+dataObj.customerName);
                }

            }
            if (command.equalsIgnoreCase("getrestaurantlist")){
                System.out.println("Executing: " + command);
                DataPacket d = new DataPacket();
                d.setRestaurantList(HandleDatabase.makeRestaurantList());
                nc.write(d.clone());
            }

            if(command.equalsIgnoreCase("askFoodListbyCategory")){
                System.out.println("Executing: " + command);
                DataPacket d = new DataPacket();
                d.sendFoodListbyCategory(HandleDatabase.makeFoodItemListByCategory(dataObj.customerEmail));
                nc.write(d.clone());
            }
            if(command.equalsIgnoreCase("askFoodListbyRestID")){
                System.out.println("Executing: " + command);
                DataPacket d = new DataPacket();
                d.sendFoodListbyCategory(HandleDatabase.makeFoodItemListByRestID(dataObj.restID));
                nc.write(d.clone());
            }

            if (command.toLowerCase().contains("send")){
                String words[] = command.split("\\$");
                /*
                words[0] = Sender Name
                words[1] = Receiver Name
                words[2] = keyword = send
                words[3] = message
                */
                Information info = clientList.get(words[1]);
                String msgToSend = words[0]+" says: " + words[3];
                Object object = msgToSend;
                System.out.println("sending.." + msgToSend);
                System.out.println("words0: " + words[0]);
                info.netConnection.write(msgToSend);
            }
        }

    }

}
