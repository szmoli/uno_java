package com.akos.uno.communication.action;

public class JoinAction extends GameAction {
    public JoinAction(String playerName) {
        super(playerName, GameActionType.JOIN);
    }
}
