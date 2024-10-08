package com.akos.uno.game;

import java.util.List;

public class Deck {
    public List<Card> drawCards(int n) {
        if (drawPile.isEmpty()) {
            // todo: implement shuffle logic
        }

        return drawPile.popCards(n);
    }

    public void addCardsToDiscardPile(List<Card> cards) {
        discardPile.pushCards(cards);
    }

    private CardPile drawPile;
    private CardPile discardPile;
}
