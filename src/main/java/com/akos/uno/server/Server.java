package com.akos.uno.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server {
    public void startServer(int port) {
        try {
            clients = new ArrayList<>();
            serverSocket = new ServerSocket(port);
            serverLogger.info("Server is listening on port: {}", serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                serverLogger.trace("New client connected");

                clients.add(new ClientHandler(clientSocket));
                clients.getLast().start();
            }
        } catch (IOException e) {
            serverLogger.error("Error starting server: {}", e.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            serverLogger.error("Error stopping server: {}", e.getMessage());
        }
    }

    public void sendMessageToClient(Socket clientSocket, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            serverLogger.error("Error sending message to client: {}", e.getMessage());
        }
    }

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    protected Logger serverLogger = LogManager.getLogger();
}
