package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {
    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<>();
        this.lastDrawnCards = new ArrayList<>();
        this.hasSaidUno = false;
    }

    public boolean drawCards(List<Card> cards) {
        lastDrawnCards.clear();
        lastDrawnCards.addAll(cards);
        return hand.addAll(cards);
    }

    public boolean drawCard(Card card) {
        lastDrawnCards.clear();
        lastDrawnCards.add(card);
        return hand.add(card);
    }

    public boolean discardCard(Card card) {
        return hand.remove(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void sayUno() {
        if (hand.size() != 1) {
            return;
        }

        this.hasSaidUno = true;
    }

    public boolean hasSaidUno() {
        return hasSaidUno;
    }

    public void setHasSaidUno(boolean b) {
        hasSaidUno = b;
    }

    public List<Card> getLastDrawnCards() {
        return lastDrawnCards;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        return this.playerName.equals(((Player) other).playerName);
    }

    private final List<Card> hand;
    private final String playerName;
    private boolean hasSaidUno;
    private final List<Card> lastDrawnCards;
    private static final Logger logger = LogManager.getLogger(); 
}
