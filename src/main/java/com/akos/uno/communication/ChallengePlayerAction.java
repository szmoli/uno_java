package com.akos.uno.communication;

public class ChallengePlayerAction extends GameAction {
    public ChallengePlayerAction(String playerName, String challengedPlayerName) {
        super(playerName, GameActionType.CHALLENGE_PLAYER);
        this.challengedPlayerName = challengedPlayerName;
    }

    private String challengedPlayerName;
}
