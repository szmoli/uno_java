package com.akos.uno.communication.action;

import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;

public class DiscardCardAction extends GameAction {
    public DiscardCardAction(String playerName, Card card, CardColor desiredColor) {
        super(playerName, GameActionType.DISCARD_CARD);
        this.card = card;
        this.desiredColor = desiredColor;
    }

    public Card getCard() {
        return card;
    }

    public CardColor getDesiredColor() {
        return desiredColor;
    }

    private final Card card;
    private final CardColor desiredColor;
}
