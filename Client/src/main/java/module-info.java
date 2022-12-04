module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires Server;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
    exports com.example.client.controllers;
    opens com.example.client.controllers to javafx.fxml;
    exports com.example.client.data;
    opens com.example.client.data to javafx.fxml;
}