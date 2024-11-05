package com.akos.uno.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;

public class ClientView {
    public ClientView(Client client) {
        this.client = client;
    }

    public void updateView(PartialGameState state) {
        logger.debug("Updated view with: {}", state);
    }

    private final Client client;
    private static final Logger logger = LogManager.getLogger();
}
