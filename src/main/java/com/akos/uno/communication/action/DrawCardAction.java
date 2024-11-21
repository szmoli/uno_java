package com.akos.uno.communication.action;

/**
 * Action to draw a card from the deck.
 */
public class DrawCardAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     * @param cardCount Number of cards to draw.
     */
    public DrawCardAction(String playerName, int cardCount) {
        super(playerName, GameActionType.DRAW_CARD);
        this.cardCount = cardCount;
    }

    /**
     * Get the number of cards to draw.
     * @return Number of cards to draw.
     */
    public int getCardCount() {
        return cardCount;
    }

    private final int cardCount;
}
