package com.example.server.database;


import com.example.server.database.factory.command.*;

import java.sql.*;
import java.util.ArrayList;

public class MyDatabase {

    private static MyDatabase instance;
    protected final Connection connection;
    protected final Statement statement;

    // Правильная, но "дорогая" по времени выполнения многопоточная версия
    // Можно ещё через class Holder или double-check(volatile) или static{}
    // а также enum
    // private static Singleton instance = new Singleton(); в getInstance возращаем просто instance
    public static synchronized MyDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new MyDatabase();
        }
        return instance;
    }

    public MyDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/salary-calculation?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    public ArrayList<String[]> insert(String query){
        Command command = new InsertCommand(statement, query);
        ArrayList<String[]> result = new ArrayList<String[]>();
        try {
            command.execute();
            statement.executeQuery("SELECT LAST_INSERT_ID()");
            ResultSet resultSet = statement.getResultSet();
            int count = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] arrayString = new String[count];
                for (int i = 1;  i <= count; i++)
                    arrayString[i - 1] = resultSet.getString(i);

                result.add(arrayString);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String[]> select(String query){
        Command command = new SelectCommand(statement, query);
        ArrayList<String[]> result = new ArrayList<String[]>();
        try {
            ResultSet resultSet = command.execute().getResultSet();
            int count = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                String[] arrayString = new String[count];
                for (int i = 1;  i <= count; i++)
                    arrayString[i - 1] = resultSet.getString(i);

                result.add(arrayString);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public void delete(String query){
        Command command = new DeleteCommand(statement, query);
        command.execute();
    }

    public void update(String query){
        Command command = new UpdateCommand(statement, query);
        command.execute();
    }

    public void close() {
        try {
            connection.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}

