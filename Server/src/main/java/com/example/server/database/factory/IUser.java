package com.example.server.database.factory;

import com.example.server.objects.Person;
import com.example.server.objects.User;

import java.util.ArrayList;

public interface IUser{
    int insert(User user);
    User selectUser(String login, String password);
    User selectUserByLogin(String login);
    ArrayList<User> selectAll();
    void delete(int id);
    void update(User user);

    User selectById(int id);

}
