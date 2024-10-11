package com.akos.uno.communication;

import com.akos.uno.game.Card;

public class DiscardCardAction extends GameAction {
    public DiscardCardAction(String playerName, Card card) {
        super(playerName);
        this.card = card;
        this.type = GameActionType.DISCARD_CARD;
    }

    @Override
    public GameActionType getActionType() {
        return type;
    }

    private Card card;
    private GameActionType type;
}
