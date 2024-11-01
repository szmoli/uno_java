package com.akos.uno.client;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class ClientController extends Thread {
    public ClientController(String playerName, String serverAddress, int serverPort, CountDownLatch serverReadyLatch) {
        this.serverReadyLatch = serverReadyLatch;
        this.client = new Client(this, new PartialGameState(new Player(playerName)));
        this.view = new ClientView(this);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.playerController = new PlayerController(client.getPlayer(), this);
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
            client.startConnection(client.getPlayer().getPlayerName(), serverAddress, serverPort);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public void stopConnection() {
        client.stopConnection();
    }

    public void setGameState(PartialGameState state) {
        client.setGameState(state);
    }

    private CountDownLatch serverReadyLatch;
    private String serverAddress;
    private int serverPort;
    private Client client;
    private ClientView view;
    private PlayerController playerController;
    private static final Logger logger = LogManager.getLogger();
}
