package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.SayUnoAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.Player;

public class SayUnoActionHandler implements GameActionHandler<SayUnoAction> {
    public SayUnoActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(SayUnoAction action) {
        logger.debug("Handling say uno action");

        Player player = gameController.getPlayers().get(action.getPlayerName());
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }

        if (player == null) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }
        
        boolean isPlayersTurn = gameController.isPlayersTurn(player);
        boolean isValidMove = gameController.getGame().getRules().isValidSayUno(player);
        boolean isTooLate = !player.equals(gameController.getPlayerWithDelta(-1));
        boolean validAction =
            !isPlayersTurn &&
            isValidMove &&
            !isTooLate;
        if (!validAction) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        player.sayUno();
        server.updateClients();
    }
    
    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
