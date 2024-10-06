package com.akos.server;

import java.io.IOException;
import java.net.*;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
public class Server {
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server socket initialized on port: " + port);
        } catch (IOException IOe) {
            // @todo: implement proper logging
            System.err.println("Error initializing server socket: " + IOe.getMessage());
        }
    }

    public void startServer() {
        if (serverSocket == null) {
            System.err.println("Server socket was not correctly initialized.");
        }

        isRunning = true;

        try {
            System.out.println("Server is listening on port: " + serverSocket.getLocalPort());

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException IOe) {
            System.err.println("Error starting server: " + IOe.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException IOe) {
            System.err.println("Error stopping server: " + IOe.getMessage());
        }
    }

    public static void main(String[] args) {

    }

    private ServerSocket serverSocket;
    private boolean isRunning;
}
