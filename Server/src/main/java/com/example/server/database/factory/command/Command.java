package com.example.server.database.factory.command;


import java.sql.Statement;

abstract public class Command {
    protected Statement statement;
    protected String query;

    public Command(Statement statement, String query) {
        this.statement = statement;
        this.query = query;
    }

    public abstract Statement execute();
}
