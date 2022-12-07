package com.example.server.database.factory.impl;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.ICategory;
import com.example.server.entities.Category;
import com.example.server.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlCategory implements ICategory {

    private final MyDatabase database;
    public SqlCategory() throws SQLException {
        database = MyDatabase.getInstance();
    }

    @Override
    public Category selectById(int id) {
        String query = "SELECT * FROM category WHERE number=" + id;
        return getOne(query);
    }

    private Category getOne(String str){
        ArrayList<String[]> result = database.select(str);
        Category category = new Category();
        for (String[] items: result){
            category.setId(Integer.parseInt(items[0]));
            category.setCoefficient(Double.parseDouble(items[1]));
        }
        return category;
    }
}
