package com.example.server.database.factory;

import com.example.server.objects.Person;
import com.example.server.objects.User;

import java.util.ArrayList;

public interface IPerson {
    int insert(Person person);
    ArrayList<Person> selectAll();
    Person selectById(int id);
    Person selectPersonByPhone(String phone);
    void delete(int id);

    void update(Person person, int id);
}
