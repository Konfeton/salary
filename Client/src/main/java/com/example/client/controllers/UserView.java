package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.server.entities.Category;
import com.example.server.entities.Person;
import com.example.server.entities.Salary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UserView {

    public TextField surnameTF;
    public TextField nameTF;
    public TextField categoryTF;
    public TextField loginTF;
    public TextField passTF;
    public TextField salaryTF;

    private Stage stage;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {
        Data data = Data.getInstance();

        data.getConnection().writeInt(5);
        data.getConnection().writeLine("Person");
        data.getConnection().writeObject(data.getUser().getId());

        Person person = (Person)data.getConnection().getObject();

        data.getConnection().writeInt(5);
        data.getConnection().writeLine("Salary");
        data.getConnection().writeObject(person.getPerson_id());

        Salary salary = (Salary) data.getConnection().getObject();

        double wage = 0.0;
        if (salary.getPerson_id()!=0) {
            data.getConnection().writeInt(5);
            data.getConnection().writeLine("Category");
            data.getConnection().writeObject(salary.getRankId());

            Category category = (Category) data.getConnection().getObject();

            switch (salary.getIdFormOfPayment()) {
                case 1001: {
                    wage = (((double) salary.getTariffRate() / 168) * category.getCoefficient()) * salary.getHoursWorked();
                    wage -= salary.getRetention();

                    break;
                }
                case 1002: {
                    wage = (((double) salary.getTariffRate() / 168) * category.getCoefficient()) * salary.getHoursWorked() + salary.getBonus();
                    wage -= salary.getRetention();

                    break;
                }
                case 1003: {
                    wage = salary.getProduced() * salary.getLaboriousness();
                    wage -= salary.getRetention();

                    break;
                }
                case 1004: {
                    double percent = ((double) salary.getPassedFirst() / salary.getProduced()) * 100;
                    wage = salary.getProduced() * salary.getLaboriousness() + (salary.getProduced() * salary.getLaboriousness() * bonus((int) Math.round(percent)));
                    wage -= salary.getRetention();
                    break;
                }
            }
        }
        surnameTF.setText(person.getLast_name());
        nameTF.setText(person.getFirst_name());
        loginTF.setText(data.getUser().getLogin());
        passTF.setText(data.getUser().getPassword());
        if (salary.getPerson_id()!=0) {
            categoryTF.setText(String.valueOf(salary.getRankId()));
            salaryTF.setText(String.valueOf(wage));
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

    public void toBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/client/user-menu.fxml"));
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
