package com.akos.uno.communication.action;

public class ChallengePlayerAction extends GameAction {
    public ChallengePlayerAction(String playerName) {
        super(playerName, GameActionType.CHALLENGE_PLAYER);
    }
}
