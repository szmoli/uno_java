package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.ChallengePlayerAction;
import com.akos.uno.communication.response.InvalidActionResponse;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardSymbol;
import com.akos.uno.game.Game;
import com.akos.uno.game.GameController;
import com.akos.uno.game.Player;

public class ChallengePlayerActionHandler implements GameActionHandler<ChallengePlayerAction> {
    public ChallengePlayerActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(ChallengePlayerAction action) {
        logger.debug("Handling challenge player action: {}", action.getAsJson());
        Game game = gameController.getGame();
        String playerName = action.getPlayerName();
        Player challengedPlayer = gameController.getPlayerWithDelta(-2);
        Player player = game.getState().getPlayers().get(playerName);
        ClientHandler clientHandler = server.getClients().get(playerName);

        if (clientHandler == null) {
            logger.error("Client handler not found for {}", playerName);
            return;
        }

        if (player == null || challengedPlayer == null) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        /*
         * 1. check if top card is actually a wild 4 card
         * 2. check if challengedPlayer has any other playable cards
         * 3. apply the penalty to the loser of the challenge
         * 4. if challengedPlayer loses 
         *   a. it's the player's turn
         *   b. the wild four gets returned to the hand of the challengedPlayer
         *   c. the player returns the 4 drawn cards to the draw pile
         *   d. the draw pile gets shuffled
         */
        boolean playerIsCorrect = player.equals(gameController.getPlayerWithDelta(-1));
        boolean topCardIsWildFour = gameController.getTopCard().getSymbol() == CardSymbol.WILD_FOUR;
        if (!topCardIsWildFour || !playerIsCorrect) {
            clientHandler.sendMessageToClient(new InvalidActionResponse().getAsJson());
            return;
        }

        boolean challengedPlayerHasMatchingColorCard = game.getRules().hasMatchingColorCard(challengedPlayer);
        // challengedPlayer loses
        if (challengedPlayerHasMatchingColorCard) {
            challengedPlayer.drawCards(gameController.drawCards(6)); // draw 6 cards
            Card wildFourTopCard = game.getState().getDeck().getDiscardPile().popCard(); // pop the wild four from the discard pile
            challengedPlayer.drawCard(wildFourTopCard); // add the wild four back into the player hand
            
            gameController.getGame().getState().getDeck().getDrawPile().pushCards(player.getLastDrawnCards()); // add back drawn cards to draw pile
            gameController.shuffleDrawPile(); // shuffle it again
            player.getHand().removeAll(player.getLastDrawnCards()); // remove the drawn cards from the player
            player.getLastDrawnCards().clear();

            gameController.selectPlayerWithDelta(-1); // select the player for the next turn
        }

        server.updateClients();
    }

    private final GameController gameController;
    private final Server server;
    private static final Logger logger = LogManager.getLogger();
}
