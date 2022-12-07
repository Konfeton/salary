package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.server.entities.Person;
import com.example.server.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditAdmin {

    public TextField login;
    public TextField password;
    public TextField surname;
    public TextField name;
    public TextField phone;
    public Label helpLabel;

    private Stage stage;

    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();
        login.setText(data.getUser().getLogin());
        password.setText(data.getUser().getPassword());

        data.getConnection().writeInt(5);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(data.getUser().getId());

        Person person = (Person) data.getConnection().getObject();

        surname.setText(person.getLast_name());
        name.setText(person.getFirst_name());
        phone.setText(person.getPhone());

    }

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/admin-page.fxml"));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void save(ActionEvent event) throws IOException, ClassNotFoundException {

        if (!isFilled())
            return;

        Data data = Data.getInstance();
        data.getConnection().writeInt(3);
        data.getConnection().writeLine("User");

        User user = new User();
        user.setId(data.getUser().getId());
        user.setLogin(login.getText());
        user.setPassword(password.getText());
        user.setRole("ADMIN");

        data.getConnection().writeObject(user);

        data.getConnection().writeInt(3);
        data.getConnection().writeLine("Person");

        Person person = new Person(data.getUser().getId(), name.getText(), surname.getText(), phone.getText());
        data.getConnection().writeObject(person);
        data.getConnection().writeObject(data.getUser().getId());

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/admin-page.fxml"));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean isFilled() throws IOException, ClassNotFoundException {

        if (login.getText().equals("") || password.getText().equals("") || surname.getText().equals("") || name.getText().equals("") || phone.getText().equals("")){
            helpLabel.setText("Не все поля заполнены");
            return false;
        }


        return true;
    }
}
