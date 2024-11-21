package com.akos.uno.client;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;
import com.akos.uno.gui.GamePanel;

/**
 * ClientController class for controlling the client-side game logic and communication with the server.
 */
public class ClientController extends Thread {
    /**
     * Constructs a new ClientController instance.
     * @param playerName The name of the player connecting to the server
     * @param serverAddress The server's IP address
     * @param serverPort The port number to connect to
     * @param serverReadyLatch The latch to wait for the server to be ready
     * @param gamePanel The game panel to display the game state
     */
    public ClientController(String playerName, String serverAddress, int serverPort, CountDownLatch serverReadyLatch, GamePanel gamePanel) {
        this.serverReadyLatch = serverReadyLatch;
        this.client = new Client(new PartialGameState(new Player(playerName)), this);
        this.view = new ClientView(gamePanel);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.playerController = new PlayerController(this);
    }

    /**
     * Constructs a new ClientController instance.
     * @param playerName The name of the player connecting to the server
     * @param serverAddress The server's IP address
     * @param serverPort The port number to connect to
     * @param gamePanel The game panel to display the game state
     */
    public ClientController(String playerName, String serverAddress, int serverPort, GamePanel gamePanel) {
        this.serverReadyLatch = new CountDownLatch(0);
        this.client = new Client(new PartialGameState(new Player(playerName)), this);
        this.view = new ClientView(gamePanel);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.playerController = new PlayerController(this);
    }

    /**
     * Starts the client connection to the server on a new thread.
     */
    @Override
    public void run() {
        startConnection();
    }

    /**
     * Returns the client instance managed by this controller.
     * @return The client instance
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the view instance managed by this controller.
     * @return The view instance
     */
    public PlayerController getPlayerController() {
        return playerController;
    } 

    /**
     * Starts the client connection to the server.
     */
    public void startConnection() {
        try {
            serverReadyLatch.await();
            client.startConnection(client.getGameState().getPlayer().getPlayerName(), serverAddress, serverPort);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stops the client connection to the server.
     */
    public void stopConnection() {
        client.stopConnection();
    }

    /**
     * Sets the game state of the client.
     * @param state The new game state to set
     */
    public void setGameState(PartialGameState state) {
        client.setGameState(state);
    }

    /**
     * Updates the view with the new game state.
     * @param state The new game state to display
     */
    public void updateView(PartialGameState state) {
        view.updateView(state);
    }

    /**
     * The latch indicates whether the server is ready to receive connections.
     */
    private final CountDownLatch serverReadyLatch;
    private final String serverAddress;
    private final int serverPort;
    private final Client client;
    private final ClientView view;
    private final PlayerController playerController;
    private static final Logger logger = LogManager.getLogger();
}
