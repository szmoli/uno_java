package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.StartAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.GameStatus;
import com.akos.uno.game.Player;

public class StartActionHandler implements GameActionHandler<StartAction> {
    public StartActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(StartAction action) {
        logger.debug("Handling start action");

        Player player = gameController.getPlayers().get(action.getPlayerName());
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }

        if (player == null || !player.equals(gameController.getHostPlayer()) || gameController.getGame().getState().getGameStatus() == GameStatus.IN_PROGRESS) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        gameController.startGame();

        server.updateClients();
    }
    
    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
