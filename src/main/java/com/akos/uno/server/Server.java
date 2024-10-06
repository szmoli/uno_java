package com.akos.uno.server;

import java.io.IOException;
import java.net.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server {
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverLogger.info("Server socket initialized on port: {}", port);
        } catch (IOException e) {
            serverLogger.error("Error initializing server socket: {}", e.getMessage());
        }
    }

    public void startServer() {
        if (serverSocket == null) {
            serverLogger.error("Server socket was not correctly initialized.");
        }

        try {
            serverLogger.trace("Server is listening on port: {}", serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                serverLogger.trace("New client connected");

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException IOe) {
            serverLogger.error("Error starting server: {}", IOe.getMessage());
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
            serverLogger.error("Error stopping server: {}", IOe.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1326);
        server.startServer();
    }

    private ServerSocket serverSocket;
    protected Logger serverLogger = LogManager.getLogger();
}
