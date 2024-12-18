package com.akos.uno.game;

import java.util.List;

/**
 * Represents the deck of cards in the game.
 */
public class Deck {
    /**
     * Draws a specified number of cards from the draw pile.
     * If there aren't enough cards in the draw pile, the discard pile is shuffled and its cards are transferred to the draw pile.
     * @param n The number of cards to draw
     * @return A list of the drawn cards
     */
    public List<Card> drawCards(int n) {
        // if there aren't enough cards in the draw pile:
        // - take all the cards except the last discarded one from the discard pile
        // - transfer them to the draw pile
        // - shuffle the draw pile
        if (drawPile.size() < n) {
            Card topCard = discardPile.popCard(); // save the last discarded card

            // transfer all the other cards to the draw pile
            while (!discardPile.isEmpty()) {
                drawPile.pushCard(discardPile.popCard());
            }
            shuffle();

            discardPile.pushCard(topCard); // add the last discarded card back so the game can progress from where it was left before the shuffling
        }

        return drawPile.popCards(n);
    }

    /**
     * Shuffles the draw pile.
     */
    public void shuffle() {
        drawPile.shuffle();
    }

    /**
     * Adds a list of cards to the discard pile.
     * @param cards The cards to add
     */
    public void addCardsToDiscardPile(List<Card> cards) {
        discardPile.pushCards(cards);
    }

    /**
     * Adds a single card to the discard pile.
     * @param card The card to add
     */
    public void addCardToDiscardPile(Card card) {
        discardPile.pushCard(card);
    }

    /**
     * Constructs a new Deck instance.
     * @param doShuffle Whether to shuffle the deck after initializing it
     */
    public Deck(boolean doShuffle) {
        drawPile = new CardPile();
        discardPile = new CardPile();

        for (CardSymbol symbol : CardSymbol.values()) {
            if (symbol == CardSymbol.ZERO) {
                addColoredCards(symbol, 1); // adds only one set of zero cards
            } else if (symbol != CardSymbol.WILD && symbol != CardSymbol.WILD_FOUR) {
                addColoredCards(symbol, 2); // adds two sets of non-zero cards
            }
        }

        addWildCards(); // adds 4 of each wild card
        if (doShuffle) {
            drawPile.shuffle();
        }
    }

    /**
     * Returns the draw pile.
     * @return The draw pile
     */
    public CardPile getDrawPile() {
        return drawPile;
    }

    /**
     * Returns the discard pile.
     * @return The discard pile
     */
    public CardPile getDiscardPile() {
        return discardPile;
    }

    private final CardPile drawPile;
    private final CardPile discardPile;

    // adds specified number of cards of each regular color
    private void addColoredCards(CardSymbol symbol, int count) {
        for (CardColor color : CardColor.values()) {
            if (color != CardColor.WILD) {
                for (int i = 0; i < count; i++) {
                    drawPile.pushCard(new Card(color, symbol));
                }
            }
        }
    }

    // adds 4 of each wild card
    private void addWildCards() {
        for (int i = 0; i < 4; i++) {
            drawPile.pushCard(new Card(CardColor.WILD, CardSymbol.WILD));
            drawPile.pushCard(new Card(CardColor.WILD, CardSymbol.WILD_FOUR));
        }
    }
}
