package ru.geekbrains.java2.lesson6.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 9475);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client is ready to work.");

            new Thread(() -> {
                Scanner scannerClient = new Scanner(System.in);

                while (true) {
                    try {
                        System.out.println("Client input: ");
                        out.writeUTF(scannerClient.next());
                    } catch (Exception e) {
                        System.out.println("Transmission closed.");
                    }
                }
            })
                    .start();

            while (true) {
                try {
                    System.out.println("Server output: " + in.readUTF());
                } catch (Exception e) {
                    System.out.println("Connection to the server is closed.");
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }
}

