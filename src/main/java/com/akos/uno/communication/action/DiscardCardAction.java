package com.akos.uno.communication.action;

import com.akos.uno.game.Card;

public class DiscardCardAction extends GameAction {
    public DiscardCardAction(String playerName, Card card) {
        super(playerName, GameActionType.DISCARD_CARD);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    private Card card;
}
