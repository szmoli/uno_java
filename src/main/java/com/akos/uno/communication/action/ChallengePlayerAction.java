package com.akos.uno.communication.action;

public class ChallengePlayerAction extends GameAction {
    public ChallengePlayerAction(String playerName, String challengedPlayerName) {
        super(playerName, GameActionType.CHALLENGE_PLAYER);
        this.challengedPlayerName = challengedPlayerName;
    }

    private String challengedPlayerName;
}
