package com.example.server.database.factory;

import com.example.server.entities.Person;

import java.util.ArrayList;

public interface IPerson {
    int insert(Person person);
    ArrayList<Person> selectAll();
    Person selectById(int id);
    Person selectPersonByPhone(String phone);
    void delete(int id);
    ArrayList<Person> selectBySurname(String surname);

    void update(Person person, int id);
}
