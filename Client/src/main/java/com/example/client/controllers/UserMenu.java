package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.server.entities.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMenu {

    public Label helloLabel;
    private Stage stage;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();

        data.getConnection().writeInt(5);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(data.getUser().getId());

        Person person = (Person)data.getConnection().getObject();
        Data.getInstance().setPersonId(person.getPerson_id());


        helloLabel.setText("Приветствую, " + person.getFirst_name());
    }

    public void toBack(ActionEvent event) {
        try {
            changePage(event, "/com/example/client/hello-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Data.getInstance().setUser(null);
    }

    public void toShowPersonalInfo(ActionEvent event) {
        try {
            changePage(event, "/com/example/client/user-page.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void toShowSalary(ActionEvent event) {
        try {
            //TODO: show-salary
            changePage(event, "/com/example/client/edit-salary.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void changePage(ActionEvent event, String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
