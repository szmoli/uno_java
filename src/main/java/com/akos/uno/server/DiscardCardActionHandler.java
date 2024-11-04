package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
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
        CardColor desiredColor = action.getDesiredColor();
        
        ClientHandler clientHandler = server.getClients().get(action.getPlayerName());
        if (clientHandler == null) {
            logger.error("Client handler not found for {}", action.getPlayerName());
            return;
        }

        Player player = gameController.getPlayers().get(action.getPlayerName());
        boolean playerExists = player != null;
        boolean isPlayersTurn = gameController.isPlayersTurn(player);
        boolean isValidMove = rules.isValidMove(card);
        boolean validAction = 
            playerExists && // player doesn't exist
            isPlayersTurn && // it's not the player's turn
            isValidMove; // it's an invalid move
        if (!validAction) {
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

        // if a wild card was played change it's color to the desired color
        if (gameController.isWildCard(card)) {
            if (desiredColor == CardColor.NONE) {
                desiredColor = CardColor.RED;
                logger.error("desired color is none, defaulted to red");
            }

            card = new Card(desiredColor, card.getSymbol());
        }

        // log an error if card couldn't be added for some reason
        if (!gameController.addCardToDiscardPile(card)) {
            logger.error("Could not add {} card to discard pile", card);
        }

        Player playerToApplyEffectsTo = gameController.getPlayerWithDelta(1);
        gameController.applyCardEffects(card, playerToApplyEffectsTo); // apply action card effects
        gameController.selectPlayer(1); // select next player
        server.updateClients();
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
