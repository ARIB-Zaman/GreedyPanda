
package com.example.javafx_food_project.Communication;

import com.example.javafx_food_project.HandleDatabase;
import com.example.javafx_food_project.misc_classes.DataPacket;

import java.util.HashMap;

/**
 *
 * @author user
 */
public class CreateConnection implements Runnable{
    
    HashMap<String, Information> clientList;
    NetworkConnection nc;
    public CreateConnection(HashMap<String,Information> cList, NetworkConnection nConnection){
        clientList=cList;
        nc=nConnection;    
    }
        
    
    @Override
    public void run() {
        Object userObj=nc.read();
        DataPacket loginData=(DataPacket)userObj;
        boolean verified = false;
        System.out.println("UserType : "+loginData.usertype+" connected");

        if(loginData.usertype.equalsIgnoreCase("customer")){
            while(!verified){
                verified = HandleDatabase.verifyCustomer(loginData.customerName, loginData.customerPass);
                if (verified){
                    clientList.put(loginData.usertype,new Information(loginData.usertype,nc));
                    System.out.println("Customer HashMap updated"+clientList);
                    loginData.setCommand("verified");
                    nc.write(loginData.clone());
                }
                else{
                    loginData.setCommand("unverified");
                    nc.write(loginData.clone());
                    Object obj = nc.read();
                    loginData = (DataPacket) obj;
                }
            }
        }

        new Thread(new ReaderWriterServer(loginData.usertype, nc, clientList)).start();
        
    }
    
}
