package com.akos.uno.communication;

import com.akos.uno.game.Card;

public class JumpInAction extends GameAction {
    public JumpInAction(String playerName, Card card) {
        super(playerName);
        this.card = card;
        this.type = GameActionType.JUMP_IN;
    }

    @Override
    public GameActionType getActionType() {
        return type;
    }

    private Card card;
    private GameActionType type;
}
