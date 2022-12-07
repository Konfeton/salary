package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.PersonWithSalary;
import com.example.server.entities.*;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SalaryTableController {
    public TableView<PersonWithSalary> table;
    public TableColumn<PersonWithSalary, String> columnSurname;
    public TableColumn<PersonWithSalary, String> columnName;
    public TableColumn<PersonWithSalary, Double> columnSalary;
    public TableColumn<PersonWithSalary, String> columnFormOfPayment;
    public TableColumn<PersonWithSalary, Integer> columnRank;
    public TextField findField;

    private Stage stage;
    private Parent root;
    private ArrayList<PersonWithSalary> list = new ArrayList<>();

    private double salaryNow = 0.0;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        getAllUsers();
        refreshTable();

        TableView.TableViewSelectionModel<PersonWithSalary> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<PersonWithSalary>() {

            @Override
            public void changed(ObservableValue<? extends PersonWithSalary> observableValue, PersonWithSalary oldVal, PersonWithSalary newVal) {
                if (newVal != null) {
                    Data.getInstance().setPersonId(newVal.getPersonId());
                    salaryNow = newVal.getSalary();
                    System.out.println(Data.getInstance().getPersonId());
                }
            }
        });
    }

    private void refreshTable(){
        columnSurname.setCellValueFactory(new PropertyValueFactory<PersonWithSalary, String>("surname"));
        columnName.setCellValueFactory(new PropertyValueFactory<PersonWithSalary, String>("name"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<PersonWithSalary, Double>("salary"));
        columnFormOfPayment.setCellValueFactory(new PropertyValueFactory<PersonWithSalary, String>("formOfPayment"));
        columnRank.setCellValueFactory(new PropertyValueFactory<PersonWithSalary, Integer>("rank"));
        ObservableList<PersonWithSalary> arrayList = FXCollections.observableArrayList(list);

        table.setItems(arrayList);
    }

    private void getAllUsers() throws IOException, ClassNotFoundException {
        list.clear();
        Data data = Data.getInstance();
        data.getConnection().writeInt(4);
        data.getConnection().writeLine("Person");
        ArrayList<Person> persons = (ArrayList<Person>) data.getConnection().getObject();
        addingToList(data, persons);
    }

    private void addingToList(Data data, ArrayList<Person> persons) throws IOException, ClassNotFoundException {
        for(Person person : persons) {
            data.getConnection().writeInt(5);
            data.getConnection().writeLine("Salary");
            data.getConnection().writeObject(person.getPerson_id());

            Salary salary = (Salary) data.getConnection().getObject();

            data.getConnection().writeInt(5);
            data.getConnection().writeLine("Category");
            data.getConnection().writeObject(salary.getRankId());

            Category category = (Category) data.getConnection().getObject();

            data.getConnection().writeInt(5);
            data.getConnection().writeLine("FormOfPayment");
            data.getConnection().writeObject(salary.getIdFormOfPayment());
            FormOfPayment formOfPayment = (FormOfPayment) data.getConnection().getObject();
            double wage = 0.0;
            if (formOfPayment != null) {
                switch (formOfPayment.getId()) {
                    case 1001: {
                        wage = (((double) salary.getTariffRate() / 168) * category.getCoefficient()) * salary.getHoursWorked();
                        wage-=salary.getRetention();

                        break;
                    }
                    case 1002: {
                        wage = (((double) salary.getTariffRate() / 168) * category.getCoefficient()) * salary.getHoursWorked() + salary.getBonus();
                        wage-=salary.getRetention();

                        break;
                    }
                    case 1003: {
                        wage = salary.getProduced() * salary.getLaboriousness();
                        wage-=salary.getRetention();

                        break;
                    }
                    case 1004: {
                        double percent = ((double) salary.getPassedFirst() / salary.getProduced()) * 100;
                        wage = salary.getProduced() * salary.getLaboriousness() + (salary.getProduced() * salary.getLaboriousness() * bonus((int) Math.round(percent)));
                        wage-=salary.getRetention();
                        break;
                    }
                }
            }


            PersonWithSalary personWithSalary = new PersonWithSalary();
            personWithSalary.setPersonId(person.getPerson_id());
            personWithSalary.setSurname(person.getLast_name());
            personWithSalary.setName(person.getFirst_name());
            if (salary.getPerson_id() == 0){
                personWithSalary.setSalary(0.0);
                personWithSalary.setFormOfPayment("-");
                personWithSalary.setRank(0);
            }else {
                personWithSalary.setSalary(wage);
                personWithSalary.setFormOfPayment(formOfPayment.getForm());
                personWithSalary.setRank(salary.getRankId());
            }

            list.add(personWithSalary);
        }
    }

    private double bonus(int percent){
        if (percent == 100){
            return 1.3;
        }else {
            if (percent >97 && percent < 100){
                return 1.25;
            }else {
                if (percent < 97 && percent > 92) {
                    return 1.14;
                }
                else {
                    if (percent > 85 && percent < 92) {
                        return 1.0;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    public void findPerson(ActionEvent event) throws IOException, ClassNotFoundException {
        if (!findField.getText().equals("")) {
            Data data = Data.getInstance();
            data.getConnection().writeObject(6);
            data.getConnection().writeObject("Person");
            data.getConnection().writeObject(findField.getText());
            list.clear();
            ArrayList<Person> persons = (ArrayList<Person>) data.getConnection().getObject();
            addingToList(data, persons);
            refreshTable();

        }else{
            getAllUsers();
            refreshTable();

        }
    }

    public void editSalary(ActionEvent event) {
        if (salaryNow > 0){
            changePage(event, "/com/example/client/edit-salary.fxml");
        }
    }


    public void toBack(ActionEvent event) {
        changePage(event, "/com/example/client/admin-page.fxml");

    }

    public void calculateSalary(ActionEvent event) {
        changePage(event, "/com/example/client/salary-calc.fxml");
    }


    public void deleteSalary(ActionEvent event) {
        try {
            Data data = Data.getInstance();
            data.getConnection().writeInt(7);
            data.getConnection().writeLine("Salary");
            data.getConnection().writeObject(data.getPersonId());
            getAllUsers();
            refreshTable();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
}
