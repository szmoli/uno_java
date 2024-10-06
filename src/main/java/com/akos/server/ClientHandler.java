package com.akos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// sources:
// - https://www.w3schools.com/java/java_threads.asp
public class ClientHandler extends Thread {
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    // creates a new thread for the client
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException IOe) {
            System.err.println("Error running client thread: " + IOe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException IOe) {
                System.err.println("Error running client thread: " + IOe.getMessage());
            }
        }
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
}
