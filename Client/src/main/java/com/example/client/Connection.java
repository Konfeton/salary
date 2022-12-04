package com.example.client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Connection(InetAddress ip, int port) throws IOException {
        socket = new Socket(ip, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }


    public void writeInt(int number) throws IOException {
        out.writeObject(number);
    }

    public void writeLine(String text) throws IOException {
        out.writeObject(text);
    }

    public void writeObject(Object obj) throws IOException {
        out.writeObject(obj);
    }

    public Object getObject() throws IOException, ClassNotFoundException {
        return in.readObject();
    }
}