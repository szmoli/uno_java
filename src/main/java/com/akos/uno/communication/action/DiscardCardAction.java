package com.akos.uno.communication.action;

import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;

/**
 * Action to discard a card.
 */
public class DiscardCardAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Name of the acting player.
     * @param card Card to discard.
     * @param desiredColor Desired color of the card. Only affects wild cards.
     */
    public DiscardCardAction(String playerName, Card card, CardColor desiredColor) {
        super(playerName, GameActionType.DISCARD_CARD);
        this.card = card;
        this.desiredColor = desiredColor;
    }

    /**
     * Get the card to discard.
     * @return Card to discard.
     */
    public Card getCard() {
        return card;
    }

    /**
     * Get the desired color of the card.
     * @return Desired color of the card.
     */
    public CardColor getDesiredColor() {
        return desiredColor;
    }

    private final Card card;
    private final CardColor desiredColor;
}
