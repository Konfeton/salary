package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
import com.example.server.objects.Person;
import com.example.server.objects.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class UserControl {


    public TableView<UserPerson> table;
    public TableColumn<UserPerson, String> fieldSurname;
    public TableColumn<UserPerson, String> fieldName;
    public TableColumn<UserPerson, String> fieldRole;
    public TableColumn<UserPerson, String> fieldPhone;
    public TableColumn<UserPerson, String> fieldLogin;
    public TableColumn<UserPerson, String> fieldPass;

    private Stage stage;
    private Parent root;

    private ArrayList<UserPerson> list;
    private DirectoryChooser directoryChooser;
    private Data data;


    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        refreshTable();


        TableView.TableViewSelectionModel<UserPerson> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<UserPerson>() {

            @Override
            public void changed(ObservableValue<? extends UserPerson> observableValue, UserPerson oldVal, UserPerson newVal) {
                if (newVal != null) {
                    System.out.println(newVal.getId());
                    data.setUserToEdit(newVal);
                }
            }
        });
    }

    private void refreshTable() throws IOException, ClassNotFoundException {
        data = Data.getInstance();
        data.getConnection().writeInt(4);
        data.getConnection().writeLine("User");
        ArrayList<User> users = (ArrayList<User>) data.getConnection().getObject();
        list = new ArrayList<>();
        for (User user :
                users) {
            data.getConnection().writeInt(5);
            data.getConnection().writeLine("Person");
            data.getConnection().writeObject(user.getId());
            Person person = (Person) data.getConnection().getObject();
            UserPerson userPerson =
                    new UserPerson(user.getId(), user.getLogin(), user.getPassword(), user.getRole(),
                                    person.getPerson_id(), person.getFirst_name(), person.getLast_name(), person.getPhone());
            if (user.getId() != data.getUser().getId() && person.getUser_id() == user.getId()) {
                list.add(userPerson);
            }
        }

        fieldSurname.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("last_name"));
        fieldName.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("first_name"));
        fieldRole.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("role"));
        fieldPhone.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("phone"));
        fieldLogin.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("login"));
        fieldPass.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("password"));
        ObservableList<UserPerson> arrayList = FXCollections.observableArrayList(list);

        table.setItems(arrayList);
        data.setUserToEdit(null);
    }

    public void findPerson(ActionEvent event) {
    }

    private void changePage(ActionEvent event, String path) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void editPerson(ActionEvent event) {
        if (data.getUserToEdit() != null) {
            changePage(event, "/com/example/client/edit-user-person.fxml"); ////chenge later
        }
    }

    public void toBack(ActionEvent event) {
        changePage(event,"/com/example/client/admin-page.fxml");
    }

    public void addPerson(ActionEvent event) {
        changePage(event,"/com/example/client/registration.fxml" );
    }

    public void deletePerson(ActionEvent event) {
    }
}
