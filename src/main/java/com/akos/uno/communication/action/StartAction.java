package com.akos.uno.communication.action;

public class StartAction extends GameAction {
    public StartAction(String playerName) {
        super(playerName, GameActionType.START);
    }
}
