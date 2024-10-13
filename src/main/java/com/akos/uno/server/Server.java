package com.akos.uno.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import com.akos.uno.game.GameModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server {
    public Server(GameModel game) {
        this.game = game;
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server is listening on port: {}", serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
//                clientSocket.setKeepAlive(true); // keep alive connection because of the real-time nature of UNO
                logger.info("New client connected");

                clients.add(new ClientHandler(clientSocket));
                clients.getLast().start();
            }
        } catch (IOException e) {
            logger.error("Error starting server: {}", e.getMessage());
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
            logger.error("Error stopping server: {}", e.getMessage());
        }
    }

    public synchronized void sendMessageToClient(Socket clientSocket, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            logger.error("Error sending message to client: {}", e.getMessage());
        }
    }

    public synchronized List<ClientHandler> getClients() {
        return clients;
    }

    public synchronized void processMessage(String message) {
        logger.debug(message);
    }

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private GameModel game;
    protected Logger logger = LogManager.getLogger();
}
