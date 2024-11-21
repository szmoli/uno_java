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

/**
 * Client class for handling network connections and communication with the server.
 */
public class Client {
    /**
     * Constructs a new Client instance.
     *
     * @param gameState The partial game state to be managed by this client
     * @param clientController The controller handling client-side game logic
     */
    public Client(PartialGameState gameState, ClientController clientController) {
        this.gameState = gameState;
        this.listener = new ClientListener(clientController);
    }

    /**
     * Connects to the server at the specified address and port. Creates input and output streams. Sends the player's name to the server.
     * Starts a listener thread to handle incoming server messages.
     *
     * @param playerName The name of the player connecting to the server
     * @param address The server's IP address
     * @param port The port number to connect to
     * @throws IOException If connection cannot be established or streams cannot be created
     */
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

    /**
     * Returns the current partial game state managed by this client.
     *
     * @return The current PartialGameState instance
     */
    public synchronized PartialGameState getGameState() {
        return gameState;
    }

    /**
     * Closes the connection to the server.
     * Closes the output stream, input stream, and socket connection.
     */
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

    /**
     * Sends a message to the server.
     * @param message The message to be sent
     */
    public synchronized void sendMessageToServer(String message) {
        if (out == null) {
            clientLogger.error("Error sending message to server.");
            return;
        }

        out.println(message);
    }

    /**
     * Reads a message from the server.
     */
    public String getResponseFromServer() throws IOException {
        return in.readLine();
    }

    /**
     * Sets the partial game state managed by this client.
     * @param gameState
     */
    public synchronized void setGameState(PartialGameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the socket connection to the server.
     * @return The socket connection
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Returns the connection latch for this client.
     * @return The connection latch
     */
    public CountDownLatch getConnectionLatch() {
        return connectionLatch;
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private PartialGameState gameState;
    private final ClientListener listener;
    private final Logger clientLogger = LogManager.getLogger();
    /**
     * The connection latch is used to indicate whether the client has successfully connected to the server.
     */
    private final CountDownLatch connectionLatch = new CountDownLatch(1);
}
