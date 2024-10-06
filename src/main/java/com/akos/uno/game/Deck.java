package com.akos.uno.game;

import java.util.List;

public class Deck {
    public Card drawCard() {
        if (drawPile.isEmpty()) {
            // todo: implement shuffle logic
        }

        return drawPile.removeFirst();
    }

    public void addCardToDiscardPile(Card card) {
        discardPile.add(card);
    }

    private List<Card> drawPile;
    private List<Card> discardPile;
}
