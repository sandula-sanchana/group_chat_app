package edu.ijse.group_chatapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientUiController implements Initializable {

    DataOutputStream dos;
    DataInputStream dis;
    Socket socket;

    @FXML
    private TextArea chatArea;

    @FXML
    private ImageView imageV;

    @FXML
    private TextField messageField;

    @FXML
    void onSend(ActionEvent event) {
        try {
            String msg=messageField.getText();
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onSendImage(ActionEvent event) {

    }

    @FXML
    void selectImage(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket=new Socket("localhost",2004);
            dis=new DataInputStream(socket.getInputStream());
            dos=new DataOutputStream(socket.getOutputStream());
            new Thread(()->{
                while(true){
                    try {
                        String msg=dis.readUTF();
                        Platform.runLater(()->{
                            showMessage(msg);
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showMessage(String msg){
        chatArea.appendText(msg);
    }
}
