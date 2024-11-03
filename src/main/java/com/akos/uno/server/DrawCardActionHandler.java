package com.akos.uno.server;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.DrawCardAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.Card;
import com.akos.uno.game.Game;
import com.akos.uno.game.GameController;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class DrawCardActionHandler implements GameActionHandler<DrawCardAction> {
    public DrawCardActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(DrawCardAction action) {
        logger.debug("Handling draw card action: {}", action.getAsJson());
        Game game = gameController.getGame();
        String playerName = action.getPlayerName();
        Player player = game.getState().getPlayers().get(playerName);
        ClientHandler clientHandler = server.getClients().get(playerName);

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", playerName);
            return;
        }

        // If there is no player with the playerName or it's not their turn invalidate the action
        if (player == null || !gameController.isPlayersTurn(player)) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        // Draw cards and add them to the player's hand
        List<Card> drawnCards = gameController.drawCards(action.getCardCount());
        player.drawCards(drawnCards);

        // Move to the next round
        gameController.nextRound();

        // Update clients
        for (Player p : gameController.getGame().getState().getPlayers().values()) {
            String message = new PartialGameStateResponse(new PartialGameState(p, gameController.getGame().getState())).getAsJson();
            server.getClients().get(p.getPlayerName()).sendMessageToClient(message);
        }
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}