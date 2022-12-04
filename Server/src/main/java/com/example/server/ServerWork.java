package com.example.server;

import com.example.server.database.MyDatabase;
import com.example.server.database.factory.IPerson;
import com.example.server.database.factory.IUser;
import com.example.server.database.factory.impl.SqlPerson;
import com.example.server.database.factory.impl.SqlUser;
import com.example.server.objects.Person;
import com.example.server.objects.User;

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

            }
            case "Person": {
                Person person = (Person) in.readObject();
                int id = (int) in.readObject();
                IPerson iPerson = new SqlPerson();
                iPerson.update(person, id);

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
        }
    }

    private void searchEntity() throws IOException, ClassNotFoundException, SQLException {
//        String type = (String) in.readObject();
//        switch (type) {
//            case "User": {
//                String login = (String) in.readObject();
//                IUser iUser = new SqlUser();
//                ArrayList<User> list = iUser.findByLogin(login);
//                out.writeObject(list);
//                break;
//            }
//        }
    }

    private void getEntityById() throws IOException, ClassNotFoundException, SQLException {
        String type = (String) in.readObject();
        switch (type) {
            case "Person": {
                int id = (int) in.readObject();
                IPerson iPerson = new SqlPerson();
                Person person = iPerson.selectById(id);
                out.writeObject(person);
                break;
            }
            case "User": {
                int id = (int) in.readObject();
                IUser iUser = new SqlUser();
                User user = iUser.selectById(id);
                out.writeObject(user);
                break;
            }
        }
    }


}
