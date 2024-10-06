package com.akos.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public static void main(String[] args) {
        Client client0 = new Client();
        Client client1 = new Client();
        Client client2 = new Client();

        try {
            client0.startConnection(InetAddress.getByName("127.0.0.1"), 1326);
            client1.startConnection(InetAddress.getByName("127.0.0.1"), 1326);
            client2.startConnection(InetAddress.getByName("127.0.0.1"), 1326);
            client0.stopConnection();
            client1.stopConnection();
            client2.stopConnection();
        } catch (UnknownHostException e) {
            System.err.println("Error starting connection: " + e.getMessage());
        }
    }

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
}
