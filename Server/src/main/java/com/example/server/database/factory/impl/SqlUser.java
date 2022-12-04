package com.example.server.database.factory.impl;


import com.example.server.database.MyDatabase;
import com.example.server.database.factory.IUser;
import com.example.server.objects.Person;
import com.example.server.objects.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class SqlUser implements IUser {

    private final MyDatabase database;

    public SqlUser() throws SQLException {
        database = MyDatabase.getInstance();
    }

    @Override
    public ArrayList<User> selectAll() {
        String query = "SELECT * FROM users";
        return getAll(query);
    }

    @Override
    public int insert(User user) {
        String str = "INSERT INTO users (login, password, role) VALUES('"
                + user.getLogin() + "','" + user.getPassword() + "','"
                + user.getRole() + "')";
        ArrayList<String[]> result = database.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM users WHERE id = " + id;
        database.delete(str);
    }

    @Override
    public void update(User user) {
        String str = "UPDATE users SET login='"
                + user.getLogin()
                + "', password='"
                + user.getPassword()
                + "', role='"
                + user.getRole()
                + "'  WHERE id=" + user.getId();
        database.update(str);
    }

    @Override
    public User selectById(int id) {
        String query = "SELECT * FROM users WHERE id=" + id;
        return getOne(query);
    }

    @Override
    public User selectUser(String login, String password) {
        String str = "SELECT * FROM users WHERE login='" + login + "' AND password='" +password + "'";
        return getOne(str);
    }

    @Override
    public User selectUserByLogin(String login) {
        String query = "SELECT * FROM users WHERE login='" + login + "'";
        return getOne(query);
    }

    private ArrayList<User> getAll(String str){
        ArrayList<String[]> result = database.select(str);
        ArrayList<User> users = new ArrayList<>();
        for (String[] items: result){
            User user = new User();
            user.setId(Integer.parseInt(items[0]));
            user.setLogin(items[1]);
            user.setPassword(items[2]);
            user.setRole(items[3]);
            users.add(user);
        }
        return users;
    }

    private User getOne(String str){
        ArrayList<String[]> result = database.select(str);
        User user = new User();
        for (String[] items: result){
            user.setId(Integer.parseInt(items[0]));
            user.setLogin(items[1]);
            user.setPassword(items[2]);
            user.setRole(items[3]);
        }
        return user;
    }

}
