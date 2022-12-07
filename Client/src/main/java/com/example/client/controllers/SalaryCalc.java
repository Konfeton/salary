package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.server.entities.Person;
import com.example.server.entities.Salary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SalaryCalc {
    public ComboBox<String> comboBox;

    public TextField bonusTF;
    public TextField categoryTF;
    public TextField tariffRateTF;
    public TextField hoursWorkedTf;
    public TextField retentionTF;
    
    public VBox labels1VBox;
    public VBox inputs1VBox;
    public VBox labels2VBox;
    public VBox inputs2VBox;

    public TextField producedTF;
    public TextField passedFirstTF;
    public TextField laboriousnessTF;

    public Label helpText;
    public Label label;

    private Parent root;
    private Stage stage;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        ObservableList<String> langs = FXCollections.observableArrayList( "Простая повременная", "Повременно-премиальная", "Прямая-сдельная", "Сдельно-премиальная");
        comboBox.setItems(langs);
        comboBox.setValue("Простая повременная");

        comboBox.setOnAction(this::changeCombo);
        labels2VBox.setVisible(false);
        inputs2VBox.setVisible(false);
        bonusTF.setEditable(false);

        Data data = Data.getInstance();

        data.getConnection().writeInt(5);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(data.getPersonId());

        Person person = (Person)data.getConnection().getObject();
        label.setText("Расчёт зарплаты для " + person.getLast_name() + " " + person.getFirst_name());

    }

    private void changeCombo(ActionEvent event){
        switch (comboBox.getValue()) {
            case "Простая повременная" -> {
                labels2VBox.setVisible(false);
                inputs2VBox.setVisible(false);
                bonusTF.setEditable(false);
            }
            case "Повременно-премиальная" -> {
                bonusTF.setEditable(true);
            }
            case "Прямая-сдельная" -> {
                labels2VBox.setVisible(true);
                inputs2VBox.setVisible(true);
                passedFirstTF.setEditable(false);
                bonusTF.setEditable(false);

            }
            case "Сдельно-премиальная" -> {
                labels2VBox.setVisible(true);
                inputs2VBox.setVisible(true);
                bonusTF.setEditable(false);
                passedFirstTF.setEditable(true);

            }
        }
    }

    public void toBack(ActionEvent event) {
        changePage(event, "/com/example/client/table-of-salaries.fxml");
    }

    private boolean isFilled(){
        switch (comboBox.getValue()) {
            case "Простая повременная" -> {
                if (tariffRateTF.getText().equals("") || hoursWorkedTf.getText().equals("")
                        || retentionTF.getText().equals("") || categoryTF.getText().equals("")){
                    helpText.setText("Не все поля заполнены");
                    return false;
                }
            }
            case "Повременно-премиальная" -> {
                if (tariffRateTF.getText().equals("") || hoursWorkedTf.getText().equals("")
                        || retentionTF.getText().equals("") || categoryTF.getText().equals("")
                        || bonusTF.getText().equals("")){
                    helpText.setText("Не все поля заполнены");
                    return false;
                }
            }
            case "Прямая-сдельная" -> {
                if (tariffRateTF.getText().equals("") || hoursWorkedTf.getText().equals("")
                        || categoryTF.getText().equals("") || retentionTF.getText().equals("") || producedTF.getText().equals("")
                        || laboriousnessTF.getText().equals("")){
                    helpText.setText("Не все поля заполнены");
                    return false;
                }
            }
            case "Сдельно-премиальная" -> {
                if (tariffRateTF.getText().equals("") || hoursWorkedTf.getText().equals("")
                        || retentionTF.getText().equals("") || categoryTF.getText().equals("")
                        || producedTF.getText().equals("")  || laboriousnessTF.getText().equals("")
                        || passedFirstTF.getText().equals("")){
                    helpText.setText("Не все поля заполнены");
                    return false;
                }
            }
        }
        return true;
    }

    public void toSave(ActionEvent event) throws IOException {
        if (!isFilled())
            return;

        Salary salary = new Salary();
        switch (comboBox.getValue()) {
            case "Простая повременная" -> {
                salary.setTariffRate(Integer.parseInt(tariffRateTF.getText()));
                salary.setHoursWorked(Integer.parseInt(hoursWorkedTf.getText()));
                salary.setRetention(Double.parseDouble(retentionTF.getText()));
                salary.setIdFormOfPayment(1001);
                salary.setPerson_id(Data.getInstance().getPersonId());
                salary.setRankId(Integer.parseInt(categoryTF.getText()));
                salary.setRetention(Double.parseDouble(retentionTF.getText()));
            }
            case "Повременно-премиальная" -> {
                salary.setPerson_id(Data.getInstance().getPersonId());
                salary.setIdFormOfPayment(1002);
                salary.setBonus(Double.parseDouble(bonusTF.getText()));
                salary.setTariffRate(Integer.parseInt(tariffRateTF.getText()));
                salary.setRankId(Integer.parseInt(categoryTF.getText()));
                salary.setHoursWorked(Integer.parseInt(hoursWorkedTf.getText()));
                salary.setRetention(Double.parseDouble(retentionTF.getText()));
            }
            case "Прямая-сдельная" -> {
                salary.setPerson_id(Data.getInstance().getPersonId());
                salary.setIdFormOfPayment(1003);
                salary.setTariffRate(Integer.parseInt(tariffRateTF.getText()));
                salary.setProduced(Integer.parseInt(producedTF.getText()));
                salary.setLaboriousness(Double.parseDouble(laboriousnessTF.getText()));
                salary.setRankId(Integer.parseInt(categoryTF.getText()));
                salary.setHoursWorked(Integer.parseInt(hoursWorkedTf.getText()));
                salary.setRetention(Double.parseDouble(retentionTF.getText()));
            }
            case "Сдельно-премиальная" -> {
                salary.setPerson_id(Data.getInstance().getPersonId());
                salary.setIdFormOfPayment(1004);
                salary.setTariffRate(Integer.parseInt(tariffRateTF.getText()));
                salary.setProduced(Integer.parseInt(producedTF.getText()));
                salary.setLaboriousness(Double.parseDouble(laboriousnessTF.getText()));
                salary.setPassedFirst(Integer.parseInt(passedFirstTF.getText()));
                salary.setRankId(Integer.parseInt(categoryTF.getText()));
                salary.setHoursWorked(Integer.parseInt(hoursWorkedTf.getText()));
                salary.setRetention(Double.parseDouble(retentionTF.getText()));

            }
        }

        Data data = Data.getInstance();
        data.getConnection().writeInt(2);
        data.getConnection().writeLine("Salary");
        data.getConnection().writeObject(salary);

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/client/table-of-salaries.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
