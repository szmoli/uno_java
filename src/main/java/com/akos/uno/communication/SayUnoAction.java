package com.akos.uno.communication;

public class SayUnoAction extends GameAction {
    public SayUnoAction(String playerName) {
        super(playerName);
        this.type = GameActionType.SAY_UNO;
    }

    @Override
    public GameActionType getActionType() {
        return type;
    }

    private GameActionType type;
}
