package com.akos.uno.game;

/**
 * Represents the rules of the game.
 */
public class GameRules {
    /**
     * Constructs a new GameRules instance with the specified game.
     * @param game The game to apply the rules to
     */
    public GameRules(Game game) {
        this.game = game;
    }

    /**
     * Checks if the player has a card that matches the color of the top card in the discard pile.
     * @param player The player to check
     * @return True if the player has a matching color card, false otherwise
     */
    public boolean hasMatchingColorCard(Player player) {
        for (Card card : player.getHand())  {
            if (card.getColor() == game.getState().getDeck().getDiscardPile().getSecondCard().getColor()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the player has a card that matches the symbol or color of the top card in the discard pile.
     * @param card The card to check
     * @return True if the player has a matching card, false otherwise
     */
    public boolean isValidMove(Card card) {
        Card topCard = game.getState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() || card.getSymbol() == topCard.getSymbol() || card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR;
    }

    /**
     * Checks if the card matches the top card completely.
     * @param card The card to check
     * @return True if the card matches the top card, false otherwise
     */
    public boolean isValidJumpIn(Card card) {
        Card topCard = game.getState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() && card.getSymbol() == topCard.getSymbol();
    }

    /**
     * Checks if the player can say UNO.
     * @param player The player to check
     * @return True if the player can say UNO, false otherwise
     */
    public boolean isValidSayUno(Player player) {
        return player.getHand().size() == 1;
    }

    private final Game game;
}
