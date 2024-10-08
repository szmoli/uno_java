package com.akos.uno.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

// sources:
// - https://www.geeksforgeeks.org/stack-class-in-java/
public class CardPile {
    // adds cards to the top of the pile
    public void addCards(Collection<Card> cards) {
        for (Card card : cards) {
            this.cards.push(card);
        }
    }

    // removes n cards from the top of the pile
    public List<Card> removeCards(int n) {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("CardPile is empty.");
        }

        List<Card> removedCards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            removedCards.add(this.cards.pop());
        }

        return removedCards;
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    private Stack<Card> cards;
}
