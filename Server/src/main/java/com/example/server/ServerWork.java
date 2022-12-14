package com.example.server;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.*;
import com.example.server.database.factory.impl.*;
import com.example.server.entities.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerWork {
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private MyDatabase database;

    public ServerWork(ObjectOutputStream out, ObjectInputStream in, MyDatabase database) {
        this.out = out;
        this.in = in;
        this.database = database;
    }


    public void getOperationId(int id) throws SQLException, IOException, ClassNotFoundException {
        switch (id){
            case 1:
                signIn();
                break;
            case 2:
                saveEntityAndGetId();
                break;
            case 3:
                updateEntity();
                break;
            case 4:
                getAllEntities();
                break;
            case 5:
                getEntityById();
                break;
            case 6:
                searchEntity();
                break;
            case 7:
                deleteEntityById();
                break;
            case 8:
                findUserByLogin();
                break;
            case 9:
                findPersonByPhone();
                break;
            default:
                break;
        }
    }

    private void findPersonByPhone() throws IOException, ClassNotFoundException, SQLException {
        String phone = (String) in.readObject();
        IPerson iPerson = new SqlPerson();
        Person person = iPerson.selectPersonByPhone(phone);
        out.writeObject(person);
    }

    private void findUserByLogin() throws IOException, ClassNotFoundException, SQLException {
        String login = (String) in.readObject();
        IUser iUser = new SqlUser();
        User user = iUser.selectUserByLogin(login);
        out.writeObject(user);
    }

    private void signIn() throws IOException, SQLException, ClassNotFoundException {

        String login = (String) in.readObject();
        String password = (String) in.readObject();
        IUser iUser = new SqlUser();

        User user = iUser.selectUser(login, password);
        out.writeObject(user);
    }

    private void updateEntity() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User": {
                User user = (User) in.readObject();
                IUser iUser = new SqlUser();
                iUser.update(user);
                break;

            }
            case "Person": {
                Person person = (Person) in.readObject();
                int id = (int) in.readObject();
                IPerson iPerson = new SqlPerson();
                iPerson.update(person, id);
                break;

            }
            case "Salary": {
                Salary salary = (Salary) in.readObject();
                ISalary iSalary = new SqlSalary();
                iSalary.update(salary);
                break;

            }
        }
    }
    private void saveEntityAndGetId() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User": {
                User user = (User) in.readObject();
                IUser iUser = new SqlUser();
                int id = iUser.insert(user);
                out.writeObject(id);
                break;
            }
            case "Person": {
                Person person = (Person) in.readObject();
                IPerson iPerson = new SqlPerson();
                iPerson.insert(person);
                break;
            }
            case "Salary": {
                Salary salary = (Salary) in.readObject();
                ISalary iSalary = new SqlSalary();
                iSalary.insert(salary);
                break;
            }
        }
    }

    private void getAllEntities() throws SQLException, ClassNotFoundException, IOException {
        String type = (String) in.readObject();
        switch (type) {
            case "User": {
                IUser iUser = new SqlUser();
                ArrayList<User> list = iUser.selectAll();
                out.writeObject(list);
                break;
            }
            case "Person": {
                IPerson iPerson = new SqlPerson();
                ArrayList<Person> list = iPerson.selectAll();
                out.writeObject(list);
                break;
            }
            case "Salary": {
                ISalary iSalary = new SqlSalary();
                ArrayList<Salary> list = iSalary.selectAll();
                out.writeObject(list);
                break;
            }
        }
    }

    private void deleteEntityById() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        int id = (int) in.readObject();
        switch (type) {
            case "User": {
                IUser iUser = new SqlUser();
                iUser.delete(id);
                break;
            }
            case "Person": {
                IPerson iPerson = new SqlPerson();
                iPerson.delete(id);
                break;
            }
            case "Salary": {
                ISalary iSalary = new SqlSalary();
                iSalary.delete(id);
                break;
            }
        }
    }

    private void searchEntity() throws IOException, ClassNotFoundException, SQLException {
        String table = (String) in.readObject();
        switch (table) {
            case "Person": {
                String surname = (String) in.readObject();
                IPerson iPerson = new SqlPerson();
                out.writeObject(iPerson.selectBySurname(surname));
                break;
            }
        }
    }

    private void getEntityById() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "User": {
                int id = (int) in.readObject();
                IUser iUser = new SqlUser();
                User user = iUser.selectById(id);
                out.writeObject(user);
                break;
            }
            case "Person": {
                int id = (int) in.readObject();
                IPerson iPerson = new SqlPerson();
                Person person = iPerson.selectById(id);
                out.writeObject(person);
                break;
            }
            case "Salary": {
                int id = (int) in.readObject();
                ISalary iSalary = new SqlSalary();
                Salary salary = iSalary.selectById(id);
                out.writeObject(salary);
                break;
            }
            case "Category": {
                int id = (int) in.readObject();
                ICategory iCategory = new SqlCategory();
                Category category = iCategory.selectById(id);
                out.writeObject(category);
                break;
            }
            case "FormOfPayment": {
                int id = (int) in.readObject();
                IFormOfPayment iFormOfPayment = new SqlFormOfPayment();
                FormOfPayment formOfPayment = iFormOfPayment.selectById(id);
                out.writeObject(formOfPayment);
                break;
            }

        }
    }


}
