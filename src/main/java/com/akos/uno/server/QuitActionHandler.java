package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.QuitAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.Player;

/**
 * Handles the quit action.
 */
public class QuitActionHandler implements GameActionHandler<QuitAction> {
    /**
     * Constructor.
     * @param gameController Game controller instance
     * @param server Server instance
     */
    public QuitActionHandler(GameController gameController, Server server) {
        this.server = server;
        this.gameController = gameController;
    }

    /**
     * Handles the quit action.
     * @param action Quit action
     */
    @Override
    public void handle(QuitAction action) {
        logger.debug("Handling quit action");

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

        /*
         * 1. Remove cards from player's hand and add them back into the draw pile
         * 2. Shuffle the draw pile
         * 3. Remove player from the game
         * 4. Update clients
         */
        gameController.getGame().getState().getDeck().getDrawPile().pushCards(player.getHand()); // 1.
        player.getHand().clear(); // 1.
        gameController.shuffleDrawPile(); // 2.
        gameController.getPlayers().remove(player.getPlayerName()); // 3.
        server.disconnectClient(player.getPlayerName()); // 3.
        server.updateClients(); // 4.
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
