package com.akos.uno.game;

import java.util.List;

public class Deck {
    public List<Card> drawCards(int n) {
        // if the draw pile is empty:
        // - take all the cards except the last discarded one from the discard pile
        // - transfer them to the draw pile
        // - shuffle the draw pile
        if (drawPile.isEmpty()) {
            Card topCard = discardPile.popCard(); // save the last discarded card

            // transfer all the other cards to the draw pile
            for (Card card : discardPile) {
                drawPile.pushCard(card); // todo: this may be problematic?
            }
            drawPile.shuffle();

            discardPile.pushCard(topCard); // add the last discarded card back so the game can progress from where it was left before the shuffling
        }

        return drawPile.popCards(n);
    }

    public void addCardsToDiscardPile(List<Card> cards) {
        discardPile.pushCards(cards);
    }

    private CardPile drawPile;
    private CardPile discardPile;
}
