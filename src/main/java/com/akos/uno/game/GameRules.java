package com.akos.uno.game;

public class GameRules {
    public GameRules(Game game) {
        this.game = game;
    }

    public boolean hasMatchingColorCard(Player player) {
        for (Card card : player.getHand())  {
            if (card.getColor() == game.getState().getDeck().getDiscardPile().getSecondCard().getColor()) {
                return true;
            }
        }

        return false;
    }

    public boolean isValidMove(Card card) {
        Card topCard = game.getState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() || card.getSymbol() == topCard.getSymbol() || card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR;
    }

    public boolean isValidJumpIn(Card card) {
        Card topCard = game.getState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() && card.getSymbol() == topCard.getSymbol();
    }

    public boolean isValidSayUno(Player player) {
        return player.getHand().size() == 1;
    }


    private final Game game;
}
