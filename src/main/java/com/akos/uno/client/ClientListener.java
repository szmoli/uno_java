package com.akos.uno.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.response.PartialGameStateResponse;
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
                }
            }
        } catch (IOException e) {
            logger.error("Error while listening: {}", e.getMessage());
        }
    }

    private Client client;
    private ClientController controller;
    private static final Logger logger = LogManager.getLogger();

    private void processResponse(String responseJson) {
        logger.debug("Processing response: {}", responseJson);

        Response response = Response.createFromJson(responseJson);

        switch (response.getType()) {
            case PARTIAL_GAME_STATE:
                logger.debug("Processing partial game state");
                PartialGameState gameState = PartialGameState.createFromJson(responseJson);
                logger.debug("Deserialized PartialGameState - Player: {}, Other Player Names: {}, Other Player Hand Sizes: {}, Top Card: {}, Game Status: {}",
                gameState.getPlayer(), gameState.getOtherPlayerNames(), gameState.getOtherPlayerHandSizes(), gameState.getTopCard(), gameState.getGameStatus());
                client.setGameState(gameState);
                logger.debug("Updated game state: {}", new PartialGameStateResponse(gameState).getAsJson());
                break;
            default:
                logger.debug(response);
                break;
        }
    }
}
