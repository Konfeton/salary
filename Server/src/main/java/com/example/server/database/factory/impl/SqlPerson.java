package com.example.server.database.factory.impl;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.IPerson;
import com.example.server.entities.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public class SqlPerson implements IPerson {

    private MyDatabase database;

    public SqlPerson() throws SQLException {
        this.database = MyDatabase.getInstance();
    }
    @Override
    public ArrayList<Person> selectAll() {
        String str = "SELECT * FROM person";
        return getAll(str);
    }

    @Override
    public Person selectById(int id) {
        String query = "SELECT * FROM person WHERE user_id=" + id;
        return getOne(query);
    }

    @Override
    public Person selectPersonByPhone(String phone) {
        String query = "SELECT * FROM person WHERE phone='" + phone + "'";
        return getOne(query);
    }

    @Override
    public int insert(Person person) {
        String str = "INSERT INTO person VALUES('"
                + person.getUser_id() + "','" + person.getUser_id() +"','" + person.getLast_name() + "','"
                + person.getFirst_name() + "','" + person.getPhone() + "')";
        ArrayList<String[]> result = database.insert(str);
        return Integer.parseInt(result.get(0)[0]);
    }

    @Override
    public void delete(int id) {
        String str = "DELETE FROM person WHERE person_id = " + id;
        database.delete(str);
    }

    @Override
    public ArrayList<Person> selectBySurname(String surname) {
        String query = "SELECT * FROM person WHERE last_name LIKE '%" + surname + "%'";
        return getAll(query);
    }

    @Override
    public void update(Person person, int id) {
        String str = "UPDATE person SET last_name='"
                + person.getLast_name()
                + "', first_name='"
                + person.getFirst_name()
                + "', phone='"
                + person.getPhone()
                + "'  WHERE user_id=" + id;
        database.update(str);
    }

    private ArrayList<Person> getAll(String str){
        ArrayList<String[]> result = database.select(str);
        ArrayList<Person> persons = new ArrayList<>();
        for (String[] items: result){
            Person person = new Person();
            person.setPerson_id(Integer.parseInt(items[0]));
            person.setUser_id(Integer.parseInt(items[1]));
            person.setLast_name(items[2]);
            person.setFirst_name(items[3]);
            person.setPhone(items[4]);
            persons.add(person);
        }
        return persons;
    }

    private Person getOne(String str){
        ArrayList<String[]> result = database.select(str);
        Person person = new Person();
        for (String[] items: result){
            person.setPerson_id(Integer.parseInt(items[0]));
            person.setUser_id(Integer.parseInt(items[1]));
            person.setLast_name(items[2]);
            person.setFirst_name(items[3]);
            person.setPhone(items[4]);
        }
        return person;
    }
}
