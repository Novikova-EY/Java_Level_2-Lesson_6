package ru.geekbrains.java2.lesson6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public Server() {
        try {
            serverSocket = new ServerSocket(9475);
            System.out.println("Server is running and waiting for a connection...");
            Socket client = serverSocket.accept();
            System.out.println("Client accepted: " + client);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            new Thread(() -> {
                Scanner scannerServer = new Scanner(System.in);

                while (true) {
                    try {
                        System.out.println("Server input: ");
                        out.writeUTF(scannerServer.next());
                    } catch (Exception e) {
                        System.out.println("Connection closed.");
                    }
                }
            })
                    .start();

            while (true) {
                try {
                    System.out.println("Client output: " + in.readUTF());
                } catch (Exception e) {
                    System.out.println("Client is out.");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }
}
