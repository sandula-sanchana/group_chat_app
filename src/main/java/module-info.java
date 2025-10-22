module edu.ijse.group_chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;



    exports edu.ijse.group_chatapp;
    exports edu.ijse.group_chatapp.controller;

    opens edu.ijse.group_chatapp.controller to javafx.fxml;
    opens edu.ijse.group_chatapp to javafx.fxml;
}