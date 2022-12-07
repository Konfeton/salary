package com.example.server.database.factory.impl;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.IFormOfPayment;
import com.example.server.entities.Category;
import com.example.server.entities.FormOfPayment;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlFormOfPayment implements IFormOfPayment {

    private final MyDatabase database;

    public SqlFormOfPayment() throws SQLException {
        database = MyDatabase.getInstance();
    }

    @Override
    public FormOfPayment selectById(int id) {
        String query = "SELECT * FROM form_of_payment WHERE form_id=" + id;
        return getOne(query);
    }

    private FormOfPayment getOne(String str){
        ArrayList<String[]> result = database.select(str);
        FormOfPayment formOfPayment = new FormOfPayment();
        for (String[] items: result){
            formOfPayment.setId(Integer.parseInt(items[0]));
            formOfPayment.setForm(items[1]);
        }
        return formOfPayment;
    }
}
