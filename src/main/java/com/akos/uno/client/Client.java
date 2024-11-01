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
import com.akos.uno.game.Player;

public class Client {
    public Client(ClientController controller, PartialGameState gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    public synchronized void startConnection(String playerName, String address, int port) {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            listener = new ClientListener(this, controller);

            out.println(playerName);

            listener.start();
        } catch (IOException e) {
            clientLogger.error("Error initializing client socket: {}", e.getMessage());
        }
    }

    public PartialGameState getGameState() {
        return gameState;
    }

    public Player getPlayer() {
        return gameState.getPlayer();
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

    // public static void main(String[] args) {
    //     String name = args[0];
    //     String address = args[1];
    //     int port = Integer.parseInt(args[2]);

    //     ClientController cc = new ClientController(name);
    //     cc.startConnection(address, port);
    //     cc.sendMessageToServer(new DiscardCardAction(name, new Card(CardColor.RED, CardSymbol.SEVEN)).getAsJson());
    // }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private PartialGameState gameState;
    private ClientListener listener;
    private ClientController controller;
    private Logger clientLogger = LogManager.getLogger();
    private final CountDownLatch connectionLatch = new CountDownLatch(1);
}
