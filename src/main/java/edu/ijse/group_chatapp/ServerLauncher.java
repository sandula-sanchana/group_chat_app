package edu.ijse.group_chatapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/view/serverui.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
