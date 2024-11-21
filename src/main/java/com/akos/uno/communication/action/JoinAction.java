package com.akos.uno.communication.action;

/**
 * Action to join the game.
 */
public class JoinAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     */
    public JoinAction(String playerName) {
        super(playerName, GameActionType.JOIN);
    }
}
