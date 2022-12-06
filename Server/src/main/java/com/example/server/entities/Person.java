package com.example.server.entities;

import java.io.Serializable;

public class Person implements Serializable {
    private int person_id;
    private int user_id;
    private String first_name;
    private String last_name;
    private String phone;

    public Person() {
        this.person_id = 0;
        this.user_id = 0;
        this.first_name = "";
        this.last_name = "";
        this.phone = "";
    }

    public Person(int user_id, String first_name, String last_name, String phone) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
