package com.example.javafx_food_project.misc_classes;

import java.io.Serializable;

public class DataPacket implements Serializable,Cloneable{
    public String command;
    public String usertype;
    public String customerName;
    public String customerPass;
    public String customerEmail;

    public void DataPacket(){}

    public void cusLoginPacket(String cname, String cpass){
        usertype = "customer";
        customerName = cname;
        customerPass = cpass;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public DataPacket clone() {
        try {
            DataPacket clone = (DataPacket) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
