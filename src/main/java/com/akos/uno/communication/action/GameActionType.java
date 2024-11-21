package com.akos.uno.communication.action;

/**
 * Enum for game action types.
 * Each type corresponds to a specific action class.
 * Source: https://www.baeldung.com/a-guide-to-java-enums
 */
public enum GameActionType {
    DISCARD_CARD(DiscardCardAction.class),
    DRAW_CARD(DrawCardAction.class),
    SAY_UNO(SayUnoAction.class),
    CHALLENGE_PLAYER(ChallengePlayerAction.class),
    // JUMP_IN(JumpInAction.class),
    QUIT(QuitAction.class),
    JOIN(JoinAction.class),
    START(StartAction.class);

    private final Class<? extends GameAction> actionClass;

    /**
     * Constructor.
     * @param actionClass Class of the action corresponding to the type.
     */
    GameActionType(Class<? extends GameAction> actionClass) {
        this.actionClass = actionClass;
    }

    /**
     * Get the class of the action corresponding to the type.
     * @return Class of the action.
     */
    public Class<? extends GameAction> getActionClass() {
        return actionClass;
    }
}