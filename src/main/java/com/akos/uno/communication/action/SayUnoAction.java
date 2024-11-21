package com.akos.uno.communication.action;

/**
 * Action to say "UNO".
 */
public class SayUnoAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Acting player's name.
     */
    public SayUnoAction(String playerName) {
        super(playerName, GameActionType.SAY_UNO);
    }
}
