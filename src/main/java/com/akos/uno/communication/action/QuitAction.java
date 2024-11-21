package com.akos.uno.communication.action;

/**
 * Action to quit the game.
 */
public class QuitAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     */
    public QuitAction(String playerName) {
        super(playerName, GameActionType.QUIT);
    }
}
