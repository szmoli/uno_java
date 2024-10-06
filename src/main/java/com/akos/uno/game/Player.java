package com.akos.uno.game;

public class Player {
    public void drawCard(Card card) {
        hand.addCard(card);
    }

    public void playCard(Card card) {
        hand.removeCard(card);
    }

    private CardPile hand;
}
