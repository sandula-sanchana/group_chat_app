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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerUiController implements Initializable {
    ServerSocket serverSocket;

    List<ClientHandler> clientHandlers=new ArrayList<>();

    @FXML
    private TextArea chatArea;

    @FXML
    private ImageView imageShower;

    @FXML
    private TextField messageField;

    @FXML
    void onSend(ActionEvent event) {

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(()->{
            try {
                serverSocket=new ServerSocket(2004);
                BroadCaster broad=new BroadCaster();
                Platform.runLater(()->{
                    showMessage("server started");
                });
                while(true){
                    try{
                        Socket socket=serverSocket.accept();
                        ClientHandler clientHandler=new ClientHandler(socket,broad);
                        new Thread(clientHandler).start();
                        clientHandlers.add(clientHandler);
                        Platform.runLater(()->{
                            showMessage("new client connected");
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                Platform.runLater(() -> showMessage("❌ Failed to connect to server\n"));
                System.out.println("Client failed to connect: " + e.getMessage());
            }
        }).start();

    }

    public void showMessage(String message){
        chatArea.appendText(message);
    }
}
