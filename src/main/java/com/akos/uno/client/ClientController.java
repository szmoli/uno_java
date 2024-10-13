package com.akos.uno.client;

import com.akos.uno.communication.response.PartialGameStateResponse;
import java.io.IOException;

import com.akos.uno.game.PartialGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientController {
    public ClientController(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void startConnection(String address, int port) {
        client.startConnection(address, port);
    }

    public void stopConnection() {
        client.stopConnection();
    }

    public void sendMessageToServer(String message) {
        client.sendMessageToServer(message);
    }

    public void updateGameState(PartialGameStateResponse state) {
        try {
            String gameStateJson = client.getResponseFromServer();
            client.setGameState(PartialGameState.createFromJson(gameStateJson));
        } catch (IOException e) {
            logger.error("Error getting response from server: {}", e.getMessage());
        }
    }

    private Client client;
    private ClientView view;
    private static final Logger logger = LogManager.getLogger();
}
