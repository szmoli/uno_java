package com.akos.uno.communication.action;

public class DrawCardAction extends GameAction {
    public DrawCardAction(String playerName, int cardCount) {
        super(playerName, GameActionType.DRAW_CARD);
        this.cardCount = cardCount;
    }

    private int cardCount;
}
