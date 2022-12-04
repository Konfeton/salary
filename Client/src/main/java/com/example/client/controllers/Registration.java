package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.server.objects.Person;
import com.example.server.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Registration {
    public TextField login;
    public TextField password;
    public CheckBox checkAdmin;
    public TextField surname;
    public TextField name;
    public TextField phone;
    public Label helpLabel;

    private Stage stage;
    private String isUser = "USER";

    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/user-control.fxml"));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void save(ActionEvent event) throws IOException, ClassNotFoundException {

        if (!isFilled())
            return;

        User user = new User(login.getText(), password.getText(), isUser);

        Data data = Data.getInstance();
        data.getConnection().writeInt(2);
        data.getConnection().writeLine("User");
        data.getConnection().writeObject(user);
        int id = (int) data.getConnection().getObject();

        Person person = new Person(id, surname.getText(), name.getText(), phone.getText());
        data.getConnection().writeInt(2);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(person);

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/user-control.fxml"));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    private boolean isFilled() throws IOException, ClassNotFoundException {
        if (checkAdmin.isSelected()) {
            isUser = "ADMIN";
        }

        if (login.getText().equals("") || password.getText().equals("") || surname.getText().equals("") || name.getText().equals("") || phone.getText().equals("")){
            helpLabel.setText("Не все поля заполнены");
            return false;
        }

        Data data = Data.getInstance();
        data.getConnection().writeInt(8);
        data.getConnection().writeLine(login.getText());
        User user = (User) data.getConnection().getObject();
        if (user.getLogin().equals(login.getText())){
            helpLabel.setText("Пользователь с таким логином существует");
            return false;
        }

        data.getConnection().writeInt(9);
        data.getConnection().writeLine(phone.getText());
        Person userWithPhone = (Person) data.getConnection().getObject();
        if (phone.getText().equals(userWithPhone.getPhone())){
            helpLabel.setText("Пользователь с таким телефоном уже существует");
            return false;
        }

        return true;
    }
}
