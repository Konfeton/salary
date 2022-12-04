package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
import com.example.server.objects.Person;
import com.example.server.objects.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditUserPerson {
    public TextField login;
    public TextField password;
    public CheckBox checkAdmin;
    public TextField surname;
    public TextField name;
    public TextField phone;
    public Label helpLabel;

    private String isUser = "USER";
    private  Stage stage;

    private UserPerson userPerson = Data.getInstance().getUserToEdit();

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        login.setText(userPerson.getLogin());
        password.setText(userPerson.getPassword());
        surname.setText(userPerson.getLast_name());
        name.setText(userPerson.getFirst_name());
        phone.setText(userPerson.getPhone());
        if (userPerson.getRole().equals("ADMIN")) {
            checkAdmin.setSelected(true);
        }
    }

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
        Data data = Data.getInstance();

        User user = new User(data.getUserToEdit().getLogin(), data.getUserToEdit().getPassword(), data.getUserToEdit().getRole());
        user.setId(data.getUserToEdit().getId());

        data.getConnection().writeInt(3);
        data.getConnection().writeLine("User");
        data.getConnection().writeObject(user);

        Person person = new Person(user.getId(), data.getUserToEdit().getFirst_name(), data.getUserToEdit().getLast_name(), data.getUserToEdit().getPhone());

        data.getConnection().writeInt(3);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(person);
        data.getConnection().writeObject(user.getId());

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
        if (user.getLogin().equals(login.getText()) && !login.getText().equals(user.getLogin())){
            helpLabel.setText("Пользователь с таким логином существует");
            return false;
        }

        data.getConnection().writeInt(9);
        data.getConnection().writeLine(phone.getText());
        Person userWithPhone = (Person) data.getConnection().getObject();
        if (phone.getText().equals(userWithPhone.getPhone()) && !phone.getText().equals(userWithPhone.getPhone())){
            helpLabel.setText("Пользователь с таким телефоном уже существует");
            return false;
        }

        return true;
    }
}
