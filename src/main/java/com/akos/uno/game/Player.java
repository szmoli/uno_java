package com.akos.uno.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class Player {
    /**
     * Constructs a new Player instance.
     * @param playerName The name of the player
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.hand = new ArrayList<>();
        this.lastDrawnCards = new ArrayList<>();
        this.hasSaidUno = false;
    }

    /**
     * Draws a list of cards into the player's hand.
     * @param cards The cards to draw
     * @return True if the cards were successfully drawn, false otherwise
     */
    public boolean drawCards(List<Card> cards) {
        lastDrawnCards.clear();
        lastDrawnCards.addAll(cards);
        return hand.addAll(cards);
    }

    /**
     * Draws a single card into the player's hand.
     * @param card The card to draw
     * @return True if the card was successfully drawn, false otherwise
     */
    public boolean drawCard(Card card) {
        lastDrawnCards.clear();
        lastDrawnCards.add(card);
        return hand.add(card);
    }

    /**
     * Discards a card from the player's hand.
     * @param card The card to discard
     * @return True if the card was successfully discarded, false otherwise
     */
    public boolean discardCard(Card card) {
        return hand.remove(card);
    }

    /**
     * Returns the player's hand.
     * @return The player's hand
     */
    public List<Card> getHand() {
        return hand;
    }
    
    /**
     * Returns the player's name.
     * @return The player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets UNO status for the player.
     */
    public void sayUno() {
        if (hand.size() != 1) {
            return;
        }

        this.hasSaidUno = true;
    }

    /**
     * Returns whether the player has said UNO.
     * @return True if the player has said UNO, false otherwise
     */
    public boolean hasSaidUno() {
        return hasSaidUno;
    }

    /**
     * Sets whether the player has said UNO.
     * @param b The new UNO status
     */
    public void setHasSaidUno(boolean b) {
        hasSaidUno = b;
    }

    /**
     * Returns the last drawn cards.
     * @return The last drawn cards
     */
    public List<Card> getLastDrawnCards() {
        return lastDrawnCards;
    }

    /**
     * Compares two players for equality.
     * @param other The player to compare to
     * @return True if the players are equal, false otherwise
     */
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
}
