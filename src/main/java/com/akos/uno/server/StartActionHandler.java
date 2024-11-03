package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.StartAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class StartActionHandler implements GameActionHandler<StartAction> {
    public StartActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(StartAction action) {
        logger.debug("Handling start action");

        Player player = gameController.getGame().getState().getPlayers().get(action.getPlayerName());
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }

        if (player == null || !player.equals(gameController.getGame().getState().getHostPlayer())) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        gameController.startGame();

        // Update clients
        for (Player p : gameController.getGame().getState().getPlayers().values()) {
            String message = new PartialGameStateResponse(new PartialGameState(p, gameController.getGame().getState())).getAsJson();
            server.getClients().get(p.getPlayerName()).sendMessageToClient(message);
        }
    }
    
    private GameController gameController;
    private Server server;
    private static final Logger logger = LogManager.getLogger();
}
