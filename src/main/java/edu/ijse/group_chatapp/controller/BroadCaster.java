package edu.ijse.group_chatapp.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BroadCaster {

    List<DataOutputStream> dataOutputStreamlist=new ArrayList<>();

    public synchronized void addDataOutputStream(DataOutputStream dataOutputStream){
        dataOutputStreamlist.add(dataOutputStream);
    }

    public synchronized void broadCastToAll(String message){
        for(DataOutputStream dataOutputStream:dataOutputStreamlist){
            try {
                dataOutputStream.writeUTF(message+"\r\n");
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
