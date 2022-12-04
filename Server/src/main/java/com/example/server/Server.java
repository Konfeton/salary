package com.example.server;


import com.example.server.database.MyDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static final int PORT = 8080;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running");
        try {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("New client connected");
                try {
                    new ThreadServer(client);
                } catch (IOException e) {
                    client.close();
                }
            }
        }finally {
            serverSocket.close();
        }
    }
}
