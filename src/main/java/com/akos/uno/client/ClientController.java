package com.akos.uno.client;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class ClientController extends Thread {
    public ClientController(String playerName, String serverAddress, int serverPort, CountDownLatch serverReadyLatch) {
        this.serverReadyLatch = serverReadyLatch;
        this.client = new Client(new PartialGameState(new Player(playerName)));
        this.view = new ClientView(this.client);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.playerController = new PlayerController(this);
    }

    @Override
    public void run() {
        startConnection();
    }

    public Client getClient() {
        return client;
    }

    public PlayerController getPlayerController() {
        return playerController;
    } 

    public void startConnection() {
        try {
            serverReadyLatch.await();
            client.startConnection(client.getGameState().getPlayer().getPlayerName(), serverAddress, serverPort);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void stopConnection() {
        client.stopConnection();
    }

    public void setGameState(PartialGameState state) {
        client.setGameState(state);
    }

    private final CountDownLatch serverReadyLatch;
    private final String serverAddress;
    private final int serverPort;
    private final Client client;
    private final ClientView view;
    private final PlayerController playerController;
    private static final Logger logger = LogManager.getLogger();
}
