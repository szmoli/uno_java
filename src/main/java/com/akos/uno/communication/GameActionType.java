package com.akos.uno.communication;

// source: https://www.baeldung.com/a-guide-to-java-enums
public enum GameActionType {
    DISCARD_CARD(DiscardCardAction.class),
    DRAW_CARD(DrawCardAction.class),
    SAY_UNO(SayUnoAction.class),
    CHALLENGE_PLAYER(ChallengePlayerAction.class),
    JUMP_IN(JumpInAction.class),
    QUIT(QuitAction.class),
    JOIN(JoinAction.class),
    START(StartAction.class);

    private Class<? extends GameAction> actionClass;

    GameActionType(Class<? extends GameAction> actionClass) {
        this.actionClass = actionClass;
    }

    public Class<? extends GameAction> getActionClass() {
        return actionClass;
    }
}