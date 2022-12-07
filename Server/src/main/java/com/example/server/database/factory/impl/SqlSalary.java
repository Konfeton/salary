package com.example.server.database.factory.impl;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.ISalary;
import com.example.server.entities.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlSalary implements ISalary {

    private final MyDatabase database;

    public SqlSalary() throws SQLException {
        database = MyDatabase.getInstance();
    }

    @Override
    public int insert(Salary salary) {
        String str = "INSERT INTO salary VALUES('"
                + salary.getPerson_id() + "','" + salary.getIdFormOfPayment() + "','"
                + salary.getBonus() + "','" + salary.getTariffRate() + "','"
                + salary.getProduced() + "','" + salary.getLaboriousness() + "','"
                + salary.getPassedFirst() + "','" + salary.getRankId() + "','"
                + salary.getHoursWorked() + "','" + salary.getRetention() + "')";
        ArrayList<String[]> result = database.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public ArrayList<Salary> selectAll() {
        String str = "SELECT * FROM salary";
        return getAll(str);
    }

    @Override
    public Salary selectById(int id) {
        String str = "SELECT * FROM salary WHERE person_id =" + id;
        return getOne(str);
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM salary WHERE person_id = " + id;
        database.delete(str);
    }

    @Override
    public void update(Salary salary) {
        String str = "UPDATE salary SET form_of_payment = "
                + salary.getIdFormOfPayment() + ", bonus = "
                + salary.getBonus() + ", tariff_rate = " + salary.getTariffRate() + ", produced = "
                + salary.getProduced() + ", laboriousness = " + salary.getLaboriousness() + ", passed_first = "
                + salary.getPassedFirst() + ", level = " + salary.getRankId() + ", hours_worked = "
                + salary.getHoursWorked() + ", retention = " + salary.getRetention()
                + " WHERE person_id = " + salary.getPerson_id();
        database.update(str);
    }

    private ArrayList<Salary> getAll(String str){
        ArrayList<String[]> result = database.select(str);
        ArrayList<Salary> salaries = new ArrayList<>();
        for (String[] items: result){
            Salary salary = new Salary();
            salary.setPerson_id(Integer.parseInt(items[0]));
            salary.setIdFormOfPayment(Integer.parseInt(items[1]));
            salary.setBonus(Double.parseDouble(items[2]));
            salary.setTariffRate(Integer.parseInt(items[3]));
            salary.setProduced(Integer.parseInt(items[4]));
            salary.setLaboriousness(Double.parseDouble(items[5]));
            salary.setPassedFirst(Integer.parseInt(items[6]));
            salary.setRankId(Integer.parseInt(items[7]));
            salary.setHoursWorked(Integer.parseInt(items[8]));
            salary.setRetention(Double.parseDouble(items[9]));
            salaries.add(salary);
        }
        return salaries;
    }

    private Salary getOne(String str){
        ArrayList<String[]> result = database.select(str);
        Salary salary = new Salary();
        for (String[] items: result){
            salary.setPerson_id(Integer.parseInt(items[0]));
            salary.setIdFormOfPayment(Integer.parseInt(items[1]));
            salary.setBonus(Double.parseDouble(items[2]));
            salary.setTariffRate(Integer.parseInt(items[3]));
            salary.setProduced(Integer.parseInt(items[4]));
            salary.setLaboriousness(Double.parseDouble(items[5]));
            salary.setPassedFirst(Integer.parseInt(items[6]));
            salary.setRankId(Integer.parseInt(items[7]));
            salary.setHoursWorked(Integer.parseInt(items[8]));
            salary.setRetention(Double.parseDouble(items[9]));
        }
        return salary;
    }
}
