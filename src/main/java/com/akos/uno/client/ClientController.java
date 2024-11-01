package com.akos.uno.client;

import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientController {
    public ClientController(Player player) {
        this.client = new Client(this, new PartialGameState(player));
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

    public void setGameState(PartialGameState state) {
        client.setGameState(state);
    }

    public void processServerResponse(String response) {
        logger.debug(response);
    }

    private Client client;
    private ClientView view;
    private static final Logger logger = LogManager.getLogger();
}
