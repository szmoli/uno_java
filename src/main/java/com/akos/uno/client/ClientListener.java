package com.akos.uno.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.response.Response;
import com.akos.uno.game.PartialGameState;

public class ClientListener extends Thread {
    public ClientListener(Client client, ClientController controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            // Continuously listen for messages from the server
            while (!client.getSocket().isClosed()) {
                String serverResponse = client.getResponseFromServer();

                if (serverResponse != null) {
                    processResponse(serverResponse);
                    client.getConnectionLatch().countDown();
                }
            }
        } catch (IOException e) {
            logger.error("Error while listening: {}", e.getMessage());
        }
    }

    private Client client;
    private ClientController controller;
    private static final Logger logger = LogManager.getLogger();

    private synchronized void processResponse(String responseJson) {
        Response response = Response.createFromJson(responseJson);

        switch (response.getType()) {
            case PARTIAL_GAME_STATE:
                PartialGameState gameState = PartialGameState.createFromJson(responseJson);
                client.setGameState(gameState);
                break;
            default:
                logger.debug(response);
                break;
        }
    }
}
