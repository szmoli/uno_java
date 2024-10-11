package com.akos.uno.communication;

import com.akos.uno.game.Card;

public class JumpInAction extends GameAction {
    public JumpInAction(String playerName, Card card) {
        super(playerName, GameActionType.JUMP_IN);
        this.card = card;
    }

    private Card card;
}
