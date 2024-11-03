package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.Card;
import com.akos.uno.game.FullGameState;
import com.akos.uno.game.Game;
import com.akos.uno.game.GameController;
import com.akos.uno.game.GameRules;
import com.akos.uno.game.Player;

public class DiscardCardActionHandler implements GameActionHandler<DiscardCardAction> {
    public DiscardCardActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    public void handle(DiscardCardAction action) {
        logger.debug("Handling discard card action");

        Game game = gameController.getGame();
        FullGameState state = game.getState();
        GameRules rules = game.getRules();
        Card card = action.getCard();
        
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());
        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }

        Player player = gameController.getPlayers().get(action.getPlayerName());
        boolean invalidAction = 
            player == null || // player doesn't exist
            !gameController.isPlayersTurn(player) || // it's not the player's turn
            !rules.isValidMove(card); // it's an invalid move
        if (invalidAction) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }
        
        /*
         * If the action is valid the move gets executed here.
         * 1. Take the card from the player's hand
         * 2. Put it on top of the discard pile
         * 3. If it's an action card, apply the effects of the card
         * 4. Move on to the next round
         * 5. Updated the clients with the modified state
         */

        // log an error if player couldn't discard for some reason
        if (!player.discardCard(card)) {
            logger.error("{} could not discard the {} card", player.getPlayerName(), card);
        }

        // log an error if card couldn't be added for some reason
        if (!gameController.addCardToDiscardPile(card)) {
            logger.error("Could not add {} card to discard pile", card);
        }

        // todo: apply action card effects

        gameController.selectPlayer(1); // select next player

        server.updateClients();
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
