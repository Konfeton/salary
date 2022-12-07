package com.example.server.database.factory;

import com.example.server.entities.Salary;
import com.example.server.entities.User;

import java.util.ArrayList;

public interface ISalary {
    int insert(Salary salary);
    ArrayList<Salary> selectAll();
    Salary selectById(int id);
    void delete(int id);
    void update(Salary salary);

}
