package com.akos.uno.server;

import com.akos.uno.communication.action.DiscardCardAction;
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
        Game game = gameController.getGame();
        FullGameState state = game.getState();
        GameRules rules = game.getRules();
        Card card = action.getCard();
        boolean isPlayersTurn = state.getPlayerNamesInOrder().get(state.getCurrentPlayerIndex()).equals(action.getPlayerName());

        if (isPlayersTurn && gameController.addCardToDiscardPile(card)) {
            /*
            * 1. remove card from player's hand
            * 2. add card to discard pile
            */

            Player player = game.getState().getPlayers().get(action.getPlayerName());
            player.getHand().remove(card);
            rules.applyCardEffect(card);
        }
        else {
            // todo: send invalid move message
        }
    }

    private GameController gameController;
    private Server server;
}
