package com.example.server.database.factory.command;


import java.sql.SQLException;
import java.sql.Statement;

public class SelectCommand extends Command{
    public SelectCommand(Statement statement, String query) {
        super(statement, query);
    }

    @Override
    public Statement execute() {
        try {
            statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
