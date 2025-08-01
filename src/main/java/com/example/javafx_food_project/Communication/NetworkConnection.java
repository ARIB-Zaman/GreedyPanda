package com.example.javafx_food_project.Communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.javafx_food_project.misc_classes.Restaurant;


public class NetworkConnection {
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public NetworkConnection(Socket sock) throws IOException {
        socket=sock;
        oos=new ObjectOutputStream(socket.getOutputStream());
        ois=new ObjectInputStream(socket.getInputStream());
    }

    public NetworkConnection(String ip,int port) throws IOException{
        socket=new Socket(ip, port);
        oos=new ObjectOutputStream(socket.getOutputStream());
        ois=new ObjectInputStream(socket.getInputStream());
    }

    public void write(Object obj){
        try {
            oos.writeObject(obj);
        } catch (IOException ex) {
            System.out.println("Failed to write");
        }
    }

    public Object read(){
        Object obj;
        try {
            obj = ois.readObject();
        } catch (Exception ex) {
            System.out.println("Failed to read");
            return null;
        }
        return obj;
    }

    public Socket getSocket() {
        return socket;
    }
}