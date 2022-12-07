package com.example.client.controllers;

import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPage {
    private Stage stage;

    public void toShowUsers(ActionEvent event) throws IOException {
        changePage(event, "/com/example/client/user-control.fxml");
    }

    public void back(ActionEvent event) throws IOException {
        changePage(event,"/com/example/client/hello-view.fxml");
        Data.getInstance().setUser(null);
    }

    public void toShowPersonalInfo(ActionEvent event) throws IOException {
        changePage(event, "/com/example/client/edit-admin.fxml");
    }

    public void salaryCalculation(ActionEvent event) throws IOException {
        changePage(event, "/com/example/client/table-of-salaries.fxml");
    }

    private void changePage(ActionEvent event, String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void toShowStatistics(ActionEvent event) throws IOException {
        changePage(event, "/com/example/client/statistics.fxml");
    }
}
