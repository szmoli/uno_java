package com.akos.uno.client;

import com.akos.uno.communication.response.PartialGameStateResponse;
import java.io.IOException;

import com.akos.uno.game.PartialGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientController {
    public ClientController(String playerName) {
        this.client = new Client(this, new PartialGameState(playerName));
        this.view = new ClientView(this);
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

    public void processServerResponse(String response) {
        logger.debug(response);
    }

    private Client client;
    private ClientView view;
    private static final Logger logger = LogManager.getLogger();
}
