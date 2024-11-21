package com.akos.uno.communication.action;

/**
 * Action to challenge a player.
 */
public class ChallengePlayerAction extends GameAction {
    /**
     * Constructor.
     * @param playerName Name of the acting player.
     */
    public ChallengePlayerAction(String playerName) {
        super(playerName, GameActionType.CHALLENGE_PLAYER);
    }
}
