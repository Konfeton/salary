package com.example.client.controllers;

import com.example.client.data.Data;
import com.example.client.data.UserPerson;
import com.example.server.entities.Category;
import com.example.server.entities.Salary;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Statistics {
    public PieChart pieChart;
    public Label salaryLabel;
    public Button btnToFile;

    private Stage stage;
    private DirectoryChooser directoryChooser;

    private double sum = 0.0;
    private int i = 0;
    private int first = 0, second = 0, third =0, fourth = 0;

    @FXML
    public void initialize() throws IOException, ClassNotFoundException {

        directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);

        Data data = Data.getInstance();
        data.getConnection().writeInt(4);
        data.getConnection().writeLine("Salary");

        ArrayList<Salary> salaries = (ArrayList<Salary>) data.getConnection().getObject();

        for (Salary salary : salaries){
            data.getConnection().writeInt(5);
            data.getConnection().writeLine("Category");
            data.getConnection().writeObject(salary.getRankId());

            Category category = (Category) data.getConnection().getObject();

            double wage = 0.0;
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
            sum += wage;
            i++;
            if (wage>0 && wage<300){
                first++;
            }else{
                if (wage>300 && wage<500) {
                    second++;
                }else {
                    if (wage>500 && wage<1000){
                        third++;
                    }else{
                        if (wage>1000){
                            fourth++;
                        }
                    }
                }
            }
        }


        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("0-300р", first),
                        new PieChart.Data("301-500", second),
                        new PieChart.Data("501-1000", third),
                        new PieChart.Data(">1000", fourth));
        pieChartData.forEach(record ->
                record.nameProperty().bind(
                        Bindings.concat(
                                record.getName(), " количество: ", record.pieValueProperty()
                        )
                )
        );
        pieChart.getData().addAll(pieChartData);
        salaryLabel.setText("Средняя зарплата: " + sum/i + " р.");
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


    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите путь расположения файла");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public void toBack(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/client/admin-page.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage =(Stage) ((Parent) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void saveToFile(ActionEvent event) {
        File dir = directoryChooser.showDialog(btnToFile.getScene().getWindow());
        if (dir != null) {
            String way = dir.getAbsolutePath() + "/statistics.txt";
            try (FileWriter writer = new FileWriter(way, false)) {
                writer.write("0-300р: " + first + "\n" +
                        "301-500: " + second + "\n" +
                        "501-1000: " + third + "\n" +
                        ">1000: " + fourth + "\n" );
                writer.append('\n');
                writer.write("Средняя ЗП: " + sum/i);
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
