package com.akos.uno.communication.action;

/**
 * Action to start the game.
 */
public class StartAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     */
    public StartAction(String playerName) {
        super(playerName, GameActionType.START);
    }
}
