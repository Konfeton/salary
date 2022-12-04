package com.example.server;

import com.example.server.database.MyDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ThreadServer extends Thread {
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    MyDatabase database;
    public ThreadServer(Socket s) throws IOException {
        socket = s;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        try {
            database = new MyDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot connect to database");
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                ServerWork s = new ServerWork(out, in, database);
                Integer id = (Integer) in.readObject();
                s.getOperationId(id);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                database.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
