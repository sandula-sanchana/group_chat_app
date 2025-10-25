package edu.ijse.group_chatapp.controller;

import javafx.scene.image.Image;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BroadCaster {

    List<DataOutputStream> dataOutputStreamlist=new ArrayList<>();

    public synchronized void addDataOutputStream(DataOutputStream dataOutputStream){
        dataOutputStreamlist.add(dataOutputStream);
    }

    public synchronized void broadCastToAll(String type,String message){
        for(DataOutputStream dataOutputStream:dataOutputStreamlist){
            try {
                dataOutputStream.writeUTF(type);
                dataOutputStream.writeUTF(message+"\r\n");
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void broadCastImages(String type,int size,byte[] imageBytes){
         for (DataOutputStream dataOutputStream : dataOutputStreamlist) {
             try {
                 dataOutputStream.writeUTF(type);
                 dataOutputStream.writeInt(size);
                 dataOutputStream.write(imageBytes);
                 dataOutputStream.flush();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
    }
}
