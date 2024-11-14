package com.akos.uno.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;

public class Client {
    public Client(PartialGameState gameState, ClientController clientController) {
        this.gameState = gameState;
        this.listener = new ClientListener(clientController);
    }

    public synchronized void startConnection(String playerName, String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(playerName);

            listener.start();
        } catch (IOException e) {
            clientLogger.error("Error initializing client socket: {}", e.getMessage());
        }
    }

    public synchronized PartialGameState getGameState() {
        return gameState;
    }

    public synchronized void stopConnection() {
        try {
            if (out != null) {
                out.close();
            }

            if (in != null) {
                in.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            clientLogger.error("Error closing connection: {}", e.getMessage());
        }
    }

    public synchronized void sendMessageToServer(String message) {
        if (out == null) {
            clientLogger.error("Error sending message to server.");
            return;
        }

        out.println(message);
    }

    public String getResponseFromServer() throws IOException {
        return in.readLine();
    }

    public synchronized void setGameState(PartialGameState gameState) {
        this.gameState = gameState;
    }

    public Socket getSocket() {
        return socket;
    }

    public CountDownLatch getConnectionLatch() {
        return connectionLatch;
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private PartialGameState gameState;
    private final ClientListener listener;
    private final Logger clientLogger = LogManager.getLogger();
    private final CountDownLatch connectionLatch = new CountDownLatch(1);
}
