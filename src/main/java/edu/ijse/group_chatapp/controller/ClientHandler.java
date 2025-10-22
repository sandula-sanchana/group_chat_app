package edu.ijse.group_chatapp.controller;

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
               String msg=dis.readUTF();
               broadCaster.broadCastToAll(msg);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }
    }
}
