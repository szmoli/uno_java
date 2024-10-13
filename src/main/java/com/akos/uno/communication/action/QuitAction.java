package com.akos.uno.communication.action;

public class QuitAction extends GameAction {
    public QuitAction(String playerName) {
        super(playerName, GameActionType.QUIT);
    }
}
