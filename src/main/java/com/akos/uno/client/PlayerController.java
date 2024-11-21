package com.akos.uno.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.ChallengePlayerAction;
import com.akos.uno.communication.action.DiscardCardAction;
import com.akos.uno.communication.action.DrawCardAction;
import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.action.QuitAction;
import com.akos.uno.communication.action.SayUnoAction;
import com.akos.uno.communication.action.StartAction;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.Player;

/**
 * Class that handles the player's actions.
 */
public class PlayerController {
    /**
     * Constructor.
     * @param clientController The client controller.
     */
    public PlayerController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Get the player.
     * @return The player.
     */
    public Player getPlayer() {
        return clientController.getClient().getGameState().getPlayer();
    }

    /**
     * Get the player's hand.
     * @return The player's hand.
     */
    public List<Card> getHand() {
        return getPlayer().getHand();
    }

    /**
     * Get the player's hand size.
     * @return The player's hand size.
     */
    public void joinGame() {
        try {
            clientController.getClient().getConnectionLatch().await();            
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for connection latch {}", e.getMessage());
            clientController.interrupt();
        }
        clientController.getClient().sendMessageToServer(new JoinAction(getPlayer().getPlayerName()).getAsJson());
    }

    /**
     * Start the game.
     */
    public void startGame() {
        clientController.getClient().sendMessageToServer(new StartAction(getPlayer().getPlayerName()).getAsJson());
    }

    /**
     * Draw a card.
     */
    public void drawCards(int n) {
        clientController.getClient().sendMessageToServer(new DrawCardAction(getPlayer().getPlayerName(), n).getAsJson());
    }

    /**
     * Discard a card.
     * @param card The card to discard.
     * @param desiredColor The desired color. Only affects wild cards.
     */
    public void discardCard(Card card, CardColor desiredColor) {
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card, desiredColor).getAsJson());
    }

    /**
     * Discard a card.
     * @param index The index of the card to discard.
     * @param desiredColor The desired color. Only affects wild cards.
     */
    public void discardCard(int index, CardColor desiredColor) {
        if (getPlayer().getHand().isEmpty()) {
            return;
        }

        Card card = getPlayer().getHand().get(index);
        clientController.getClient().sendMessageToServer(new DiscardCardAction(getPlayer().getPlayerName(), card, desiredColor).getAsJson());
    }

    /**
     * Say "UNO".
     */
    public void sayUno() {
        clientController.getClient().sendMessageToServer(new SayUnoAction(getPlayer().getPlayerName()).getAsJson());
    }

    /**
     * Check if the player has said "UNO".
     * @return True if the player has said "UNO", false otherwise.
     */
    public boolean hasSaidUno() {
        return getPlayer().hasSaidUno();
    }

    /**
     * Quit the game.
     */
    public void quitGame() {
        clientController.getClient().sendMessageToServer(new QuitAction(getPlayer().getPlayerName()).getAsJson());
    }

    /**
     * Challenge a player to validate the use of draw four wild card.
     */
    public void challengePlayer() {
        clientController.getClient().sendMessageToServer(new ChallengePlayerAction(getPlayer().getPlayerName()).getAsJson());
    }

    private final ClientController clientController;
    private static final Logger logger = LogManager.getLogger();
}
