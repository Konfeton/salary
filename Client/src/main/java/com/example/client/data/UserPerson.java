package com.example.client.data;

import com.example.server.objects.Person;
import com.example.server.objects.User;

public class UserPerson {
    private int id;
    private String login;
    private String password;
    private String role;

    private int person_id;
    private String first_name;
    private String last_name;
    private String phone;

    public int getId() {
        return id;
    }

    public UserPerson(int id, String login, String password, String role, int person_id, String first_name, String last_name, String phone) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
