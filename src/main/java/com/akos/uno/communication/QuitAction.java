package com.akos.uno.communication;

public class QuitAction extends GameAction {
    public QuitAction(String playerName) {
        super(playerName, GameActionType.QUIT);
    }
}
