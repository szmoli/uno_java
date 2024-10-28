package com.akos.uno.server;

import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.game.*;

public class DiscardCardActionHandler implements GameActionHandler<DiscardCardAction> {
    public DiscardCardActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    public void handle(DiscardCardAction action) {
        if (action.getType() != GameActionType.DISCARD_CARD) {
            throw new IllegalStateException("Incorrect action");
        }

        GameModel game = gameController.getGame();
        FullGameState state = game.getGameState();
        GameRules rules = game.getRules();
        Card card = action.getCard();
        boolean isPlayersTurn = state.getPlayerNamesInOrder().get(state.getCurrentPlayerIndex()).equals(action.getPlayerName());

        if (isPlayersTurn && rules.isValidMove(card, state.getDeck().getDiscardPile().top())) {
            /*
            * 1. remove card from player's hand
            * 2. add card to discard pile
            */

            Player player = game.getGameState().getPlayers().get(action.getPlayerName());
            player.getHand().remove(card);
            game.getGameState().getDeck().getDiscardPile().pushCard(card);
            rules.applyCardEffect(card);
        }
    }

    private GameController gameController;
    private Server server;
}
