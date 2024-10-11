package com.akos.uno.communication;

public class ChallengePlayerAction extends GameAction {
    public ChallengePlayerAction(String playerName, String challengedPlayerName) {
        super(playerName);
        this.challengedPlayerName = challengedPlayerName;
        this.type = GameActionType.CHALLENGE_PLAYER;
    }

    @Override
    public GameActionType getActionType() {
        return type;
    }

    private String challengedPlayerName;
    private GameActionType type;
}
