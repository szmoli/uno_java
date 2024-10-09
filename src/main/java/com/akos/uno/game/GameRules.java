package com.akos.uno.game;

public class GameRules {
    public GameRules(boolean isJumpInAllowed, boolean isDrawStackingAllowed, boolean isSevenOAllowed, boolean isWildFourChallengeAllowed, int initialCardCount) {
        this.isJumpInAllowed = isJumpInAllowed;
        this.isDrawStackingAllowed = isDrawStackingAllowed;
        this.isSevenOAllowed = isSevenOAllowed;
        this.isWildFourChallengeAllowed = isWildFourChallengeAllowed;
        this.initialCardCount = initialCardCount;
    }

    public boolean isJumpInAllowed() {
        return isJumpInAllowed;
    }

    public boolean isDrawStackingAllowed() {
        return isDrawStackingAllowed;
    }

    public boolean isSevenOAllowed() {
        return isSevenOAllowed;
    }

    public boolean isWildFourChallengeAllowed() {
        return isWildFourChallengeAllowed;
    }

    public int getInitialCardCount() {
        return initialCardCount;
    }

    public boolean isValidMove(Card card, Card topCard) {
        return card.getColor() == topCard.getColor() || card.getSymbol() == topCard.getSymbol() || card.getColor() == CardColor.BLACK;
    }

    public boolean canDiscard(Player player, Card topCard) {
        for (Card card : player.getHand()) {
            if (isValidMove(card, topCard)) {
                return true;
            }
        }

        return false;
    }

    private boolean isJumpInAllowed;
    private boolean isDrawStackingAllowed;
    private boolean isSevenOAllowed;
    private boolean isWildFourChallengeAllowed;
    private int initialCardCount;
}
