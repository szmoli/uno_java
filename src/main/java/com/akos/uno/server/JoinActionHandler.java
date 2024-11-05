package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.Player;

public class JoinActionHandler implements GameActionHandler<JoinAction> {
    public JoinActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(JoinAction action) {
        logger.debug("Handling join action: {}", action.getAsJson());
        String playerName = action.getPlayerName();
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }
        
        Player player = new Player(playerName);

        // Try adding the player, if it fails then reject the action
        if (!gameController.addPlayer(player)) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        if (gameController.getHostPlayer() == null) {
            gameController.setHostPlayer(player);
        }

        server.updateClients();
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
