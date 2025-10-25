package edu.ijse.group_chatapp.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class ClientUiController implements Initializable {

    public VBox chatContainer;
    public ScrollPane scrollPane;
    DataOutputStream dos;
    DataInputStream dis;
    Socket socket;
    File file;

    @FXML
    private TextArea chatArea;

    @FXML
    private ImageView imageV;

    @FXML
    private TextField messageField;


    @FXML
    void onSend(ActionEvent event) {
        try {
            String msg = messageField.getText();
            if (msg.isEmpty()) return;

            dos.writeUTF("TEXT");
            dos.writeUTF(msg);
            dos.flush();

            chatContainer.getChildren().add(new Label("Me: " + msg));
            messageField.clear();

            scrollPane.layout();
            scrollPane.setVvalue(1.0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onSendImage(ActionEvent event) {
       if(file!=null){
           try {
               dos.writeUTF("IMAGE");
               byte[] imageBytes= Files.readAllBytes(file.toPath());
               dos.writeInt(imageBytes.length);
               dos.write(imageBytes);
               dos.flush();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }

       }
    }

    @FXML
    void selectImage(ActionEvent event) {
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image", "*.jpg"));
       file=fileChooser.showOpenDialog(null);
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
                        String type=dis.readUTF();
                        if(type.equals("TEXT")){
                            String msg=dis.readUTF();
                            Platform.runLater(()->{
                              chatContainer.getChildren().add(new Label(msg));
                            });
                        }else if(type.equals("IMAGE")){
                            int size=dis.readInt();
                            byte[] imageBytes=new byte[size];
                            dis.readFully(imageBytes);
                            ByteArrayInputStream bais=new ByteArrayInputStream(imageBytes);
                            Image image=new Image(bais);
                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(200);
                            imageView.setFitHeight(150);
                            imageView.setPreserveRatio(true);
                            Platform.runLater(()->{
                                chatContainer.getChildren().add(imageView);
                            });

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
