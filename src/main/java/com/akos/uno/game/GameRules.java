package com.akos.uno.game;

public class GameRules {
    public GameRules(GameModel game) {
        this.game = game;
    }

    public boolean isValidMove(Card card) {
        Card topCard = game.getGameState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() || card.getSymbol() == topCard.getSymbol() || card.getColor() == CardColor.BLACK;
    }

    public boolean isValidJumpIn(Card card) {
        Card topCard = game.getGameState().getDeck().getDiscardPile().top();
        return card.getColor() == topCard.getColor() && card.getSymbol() == topCard.getSymbol();
    }

    public void applyCardEffect(Card card) {
        switch (card.getSymbol()) {
            case DRAW_TWO:
                break;
            case WILD:
                break;
            case WILD_FOUR:
                break;
            case REVERSE:
                break;
            case SKIP:
                break;
            default:
                break;
        }
    }

//    public boolean enforceForgotToSayUNOPenalty(Player player, Deck deck) {
//        if (!player.hasSaidUno && player.getHand().size() == 1) {
//            return player.drawCards(deck.drawCards(forgotToSayUNOPenaltyCardCount));
//        }
//
//        return false;
//    }

    public boolean isValidJumpIn() {
        // todo: implement logic
        return true;
    }

    private GameModel game;
}
