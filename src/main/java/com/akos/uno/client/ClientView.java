package com.akos.uno.client;

import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.PartialGameState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientView {
    public ClientView(ClientController controller) {
        this.controller = controller;
    }

    public void updateView(PartialGameState state) {
        logger.debug("Updated view with: {}", state);
    }

    private ClientController controller;
    private static final Logger logger = LogManager.getLogger();
}
