package com.example.client.controllers;

import com.example.client.Connection;
import com.example.client.data.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

import com.example.server.entities.User;
import javafx.stage.Stage;

public class MainController {
    public TextField login;
    public PasswordField password;
    public Label helpText;

    private final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public Button enterButton;

    Parent root;
    Stage stage;

    public void logIn(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (login.getText().equals("") || password.getText().equals("")){
            helpText.setText("Введите незаполненные поля");
            return;
        }

        if (login.getText().matches(REGEX_EMAIL)){
            helpText.setText("Логин введён неправильно");
        }

        Data data =  Data.getInstance();
        data.getConnection().writeInt(1);
        data.getConnection().writeLine(login.getText());
        data.getConnection().writeLine(password.getText());
        User user = (User) data.getConnection().getObject();

        if (user.getId() == 0){
            helpText.setText("Такого пользователя не существует");
            return;
        }else {
            data.setUser(user);
            enterButton.getScene().getWindow().hide();

            if (Objects.equals(data.getUser().getRole(), "ADMIN")) {
                root = FXMLLoader.load(getClass().getResource("/com/example/client/admin-page.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/com/example/client/user-menu.fxml"));
            }
            Scene scene = new Scene(root);
            stage =(Stage) ((Parent)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void initialize() throws IOException {
        if (Data.getInstance().getConnection() == null) {
            Data.getInstance().setConnection(new Connection(InetAddress.getByName(null), 8080));
            System.out.println("Connected");
        }
    }
}
