package edu.ijse.group_chatapp.controller;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    BroadCaster broadCaster;

    ClientHandler(Socket socket,BroadCaster broadCaster){
        this.socket=socket;
        this.broadCaster=broadCaster;
        try {
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
       broadCaster.addDataOutputStream(dos);
       while (true){
           try {
               String type=dis.readUTF();
               if(type.equals("TEXT")){
                   String msg=dis.readUTF();
                   broadCaster.broadCastToAll(type,msg);
               }else if(type.equals("IMAGE")){
                    int size=dis.readInt();
                    byte[] imageBytes=new byte[size];
                    dis.readFully(imageBytes);
                    broadCaster.broadCastImages(type,size,imageBytes);
               }

           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
    }
}
