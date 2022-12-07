package com.example.client.data;

import com.example.client.Connection;
import com.example.server.entities.User;

public class Data {
    private User user;
    private UserPerson userToEdit;
    private int personId;
    private static Data instance;
    private Connection connection;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public UserPerson getUserToEdit() {
        return userToEdit;
    }

    public void setUserToEdit(UserPerson userToEdit) {
        this.userToEdit = userToEdit;
    }

    public static Data getInstance() {
        if (instance == null){
            instance = new Data();
        }
        return instance;
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
