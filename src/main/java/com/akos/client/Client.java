package com.akos.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public void startConnection(InetAddress address, int port) {
        try {
            clientSocket = new Socket(address, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            System.err.println("Error initializing client socket: " + ioe.getMessage());
        }
    }

    public void stopConnection() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
}
