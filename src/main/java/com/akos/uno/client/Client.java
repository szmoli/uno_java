package com.akos.uno.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.game.PartialGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    public Client(ClientController controller, PartialGameState gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    public void startConnection(String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            listener = new ClientListener(this, controller);

            listener.start();
            JoinAction joinAction = new JoinAction(gameState.getPlayer().getPlayerName());
            sendMessageToServer(joinAction.getAsJson());
        } catch (IOException e) {
            clientLogger.error("Error initializing client socket: {}", e.getMessage());
        }
    }

    public void stopConnection() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            clientLogger.error("Error closing connection: {}", e.getMessage());
        }
    }

    public void sendMessageToServer(String message) {
        if (out == null) {
            clientLogger.error("Error sending message to server.");
        }

        out.println(message);
    }

    public String getResponseFromServer() throws IOException {
        return in.readLine();
    }

    public void setGameState(PartialGameState gameState) {
        this.gameState = gameState;
    }

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private PartialGameState gameState;
    private ClientListener listener;
    private ClientController controller;
    private Logger clientLogger = LogManager.getLogger();
}
