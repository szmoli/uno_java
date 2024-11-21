package com.akos.uno.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.response.Response;
import com.akos.uno.game.PartialGameState;

/**
 * This class listens for messages from the server and processes them.
 */
public class ClientListener extends Thread {
    /**
     * Constructor.
     * @param clientController The client controller managing the listener's client.
     */
    public ClientListener(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Listens for messages from the server and processes them on a new thread.
     */
    @Override
    public void run() {
        Client client = clientController.getClient();

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

    private final ClientController clientController;
    private static final Logger logger = LogManager.getLogger();

    private synchronized void processResponse(String responseJson) {
        Response response = Response.createFromJson(responseJson);

        switch (response.getType()) {
            case PARTIAL_GAME_STATE -> {
                PartialGameState gameState = PartialGameState.createFromJson(responseJson);
                logger.debug("Processed respone:\n{}", gameState.getAsJson());
                clientController.setGameState(gameState);
                clientController.updateView(gameState);
            }
            default -> logger.debug("Default case\n{}", response);
        }
    }
}
