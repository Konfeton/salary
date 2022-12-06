package com.example.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 8080;
    public static int clientNumber = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running");
        try {
            while (true) {
                Socket client = serverSocket.accept();
                clientNumber++;
                System.out.println("Сlient " + clientNumber + " connected");
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
