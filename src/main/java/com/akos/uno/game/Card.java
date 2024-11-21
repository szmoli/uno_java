package com.akos.uno.game;

/**
 * Represents a single card in the game.
 */
public class Card {
    /**
     * Constructs a new Card instance with the specified color and symbol.
     * @param color The color of the card
     * @param symbol The symbol of the card
     */
    public Card(CardColor color, CardSymbol symbol) {
        this.color = color;
        this.symbol = symbol;
    }

    /**
     * Returns the color of the card.
     * @return The color of the card
     */
    public CardColor getColor() {
        return color;
    }

    /**
     * Returns the symbol of the card.
     * @return The symbol of the card
     */
    public CardSymbol getSymbol() {
        return symbol;
    }

    /**
     * Returns a string representation of the card.
     * @return A string representation of the card
     */
    @Override
    public String toString() {
        return "Card{" + color.name() + " " + symbol.name() + "}";
    }

    /**
     * Compares this card to another object for equality.
     * @param other The object to compare to
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        } 

        Card otherCard = (Card) other;
        return this.getColor() == otherCard.getColor() && this.getSymbol() == otherCard.getSymbol();
    }

    private final CardColor color;
    private final CardSymbol symbol;
}
