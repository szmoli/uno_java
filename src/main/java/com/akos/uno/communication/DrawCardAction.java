package com.akos.uno.communication;

public class DrawCardAction extends GameAction {
    public DrawCardAction(String playerName, int cardCount) {
        super(playerName);
        this.cardCount = cardCount;
        this.type = GameActionType.DRAW_CARD;
    }

    @Override
    public GameActionType getActionType() {
        return type;
    }

    private int cardCount;
    private GameActionType type;
}
