package com.example.server.entities;

import java.io.Serializable;

public class FormOfPayment implements Serializable {
    private int id;
    private String form;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }
}
