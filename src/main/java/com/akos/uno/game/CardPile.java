package com.akos.uno.game;

import java.util.List;

public class CardPile {
    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    List<Card> cards;
}
