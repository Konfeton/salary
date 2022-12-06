package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
import com.example.server.entities.Person;
import com.example.server.entities.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserControl {


    public TableView<UserPerson> table;
    public TableColumn<UserPerson, String> columnSurname;
    public TableColumn<UserPerson, String> columnName;
    public TableColumn<UserPerson, String> columnRole;
    public TableColumn<UserPerson, String> columnPhone;
    public TableColumn<UserPerson, String> columnLogin;
    public TableColumn<UserPerson, String> columnPass;
    public TextField findField;
    public Button btnToFile;

    private Stage stage;
    private Parent root;

    private ArrayList<UserPerson> list;
    private DirectoryChooser directoryChooser;
    private Data data = Data.getInstance();;


    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        getPersonsFromUsers();
        refreshTable();
        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);


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

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите путь расположения файла");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    private void refreshTable() throws IOException, ClassNotFoundException {

        columnSurname.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("last_name"));
        columnName.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("first_name"));
        columnRole.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("role"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("phone"));
        columnLogin.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("login"));
        columnPass.setCellValueFactory(new PropertyValueFactory<UserPerson, String>("password"));
        ObservableList<UserPerson> arrayList = FXCollections.observableArrayList(list);

        table.setItems(arrayList);
        data.setUserToEdit(null);
    }

    private void getPersonsFromUsers() throws IOException, ClassNotFoundException {
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
    }

    public void findPerson(ActionEvent event) throws IOException, ClassNotFoundException {
        if (!findField.getText().equals("")) {

            data.getConnection().writeObject(6);
            data.getConnection().writeObject("Person");
            data.getConnection().writeObject(findField.getText());
            //TODO: Закончить поиск
            list.clear();
            ArrayList<Person> persons = (ArrayList<Person>) data.getConnection().getObject();
            for (Person person :
                    persons) {
                data.getConnection().writeInt(5);
                data.getConnection().writeLine("User");
                data.getConnection().writeObject(person.getUser_id());
                User user = (User) data.getConnection().getObject();
                UserPerson userPerson =
                        new UserPerson(user.getId(), user.getLogin(), user.getPassword(), user.getRole(),
                                person.getPerson_id(), person.getFirst_name(), person.getLast_name(), person.getPhone());
                System.out.println(userPerson.toString());
                if (user.getId() != data.getUser().getId() && person.getUser_id() == user.getId()) {
                    list.add(userPerson);
                }
            }
            refreshTable();
        }else{
            getPersonsFromUsers();
            refreshTable();

        }
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
            changePage(event, "/com/example/client/edit-user-person.fxml");
        }
    }

    public void toBack(ActionEvent event) {
        changePage(event,"/com/example/client/admin-page.fxml");
    }

    public void addPerson(ActionEvent event) {
        changePage(event,"/com/example/client/registration.fxml" );
    }

    public void deletePerson(ActionEvent event) throws IOException {
        if (data.getUserToEdit() != null) {
            data.getConnection().writeInt(7);
            data.getConnection().writeObject("User");
            data.getConnection().writeObject(data.getUserToEdit().getId());

            data.getConnection().writeInt(7);
            data.getConnection().writeObject("Person");
            data.getConnection().writeObject(data.getUserToEdit().getId());
        }
        try {
            refreshTable();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void toSaveToFile(ActionEvent actionEvent) {
        File dir = directoryChooser.showDialog(btnToFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/users.txt";
            try (FileWriter writer = new FileWriter(way, false)) {
                for (UserPerson obj :
                        list) {
                    writer.write(obj.toString());
                    writer.append('\n');
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
