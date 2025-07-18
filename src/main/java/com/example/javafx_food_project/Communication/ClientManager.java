
package com.example.javafx_food_project.Communication;

import com.example.javafx_food_project.Communication.NetworkConnection;
import com.example.javafx_food_project.Communication.Reader;
import com.example.javafx_food_project.Communication.Writer;
import com.example.javafx_food_project.misc_classes.DataPacket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

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
        nc=new NetworkConnection(socket.getLocalAddress().getHostAddress(),12345);
        System.out.println("Connected!");

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
}
