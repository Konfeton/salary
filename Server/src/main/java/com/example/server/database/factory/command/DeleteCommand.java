package com.example.server.database.factory.command;


import java.sql.SQLException;
import java.sql.Statement;

public class DeleteCommand extends Command{
    public DeleteCommand(Statement statement, String query) {
        super(statement, query);
    }

    @Override
    public Statement execute() {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }
}
