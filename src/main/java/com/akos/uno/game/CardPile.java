package com.akos.uno.game;

import java.util.*;

// sources:
// - https://www.geeksforgeeks.org/stack-class-in-java/
public class CardPile {
    // adds cards to the top of the pile
    public void pushCards(Collection<Card> cards) {
        for (Card card : cards) {
            this.cards.push(card);
        }
    }

    // removes n cards from the top of the pile
    public List<Card> popCards(int n) {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("CardPile is empty.");
        }

        List<Card> removedCards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            removedCards.add(this.cards.pop());
        }

        return removedCards;
    }

    public void shuffle() {
        List<Card> cardsList = new ArrayList<>(cards);
        Collections.shuffle(cardsList);

        cards.clear();
        for (Card card : cardsList) {
            cards.push(card);
        }
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    private Stack<Card> cards;
}
