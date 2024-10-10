package com.akos.uno.game;

public class GameRules {
    public GameRules(boolean isJumpInAllowed, boolean isDrawStackingAllowed, boolean isSevenOAllowed, boolean isWildFourChallengeAllowed, int initialCardCount, int forgotToSayUNOPenaltyCardCount, int maxPlayerCount) {
        this.isJumpInAllowed = isJumpInAllowed;
        this.isDrawStackingAllowed = isDrawStackingAllowed;
        this.isSevenOAllowed = isSevenOAllowed;
        this.isWildFourChallengeAllowed = isWildFourChallengeAllowed;
        this.initialCardCount = initialCardCount;
        this.forgotToSayUNOPenaltyCardCount = forgotToSayUNOPenaltyCardCount;
        this.maxPlayerCount = maxPlayerCount;
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

    public int getForgotToSayUNOPenaltyCardCount() {
        return forgotToSayUNOPenaltyCardCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
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

    public boolean enforceForgotToSayUNOPenalty(Player player, Deck deck) {
        if (!player.hasSaidUno && player.getHand().size() == 1) {
            return player.drawCards(deck.drawCards(forgotToSayUNOPenaltyCardCount));
        }

        return false;
    }

    public boolean isValidJumpIn() {
        // todo: implement logic
        return true;
    }

    private boolean isJumpInAllowed;
    private boolean isDrawStackingAllowed;
    private boolean isSevenOAllowed;
    private boolean isWildFourChallengeAllowed;
    private int initialCardCount;
    private int forgotToSayUNOPenaltyCardCount;
    private int maxPlayerCount;
}
