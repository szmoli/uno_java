package com.akos.uno.communication.action;

/**
 * Enum for game action types.
 * Each type corresponds to a specific action class.
 * Source: https://www.baeldung.com/a-guide-to-java-enums
 */
public enum GameActionType {
    /**
     * Discard a card from the player's hand.
     */
    DISCARD_CARD(DiscardCardAction.class),
    /**
     * Draw a card from the deck.
     */
    DRAW_CARD(DrawCardAction.class),
    /**
     * Say UNO.
     */
    SAY_UNO(SayUnoAction.class),
    /**
     * Challenge a player who played a wild draw four card.
     */
    CHALLENGE_PLAYER(ChallengePlayerAction.class),
    /**
     * Quit the game.
     */
    QUIT(QuitAction.class),
    /**
     * Join the game.
     */
    JOIN(JoinAction.class),
    /**
     * Start the game.
     */
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