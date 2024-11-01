package com.akos.uno.game;

import java.util.List;

public class Deck {
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
            drawPile.shuffle();

            discardPile.pushCard(topCard); // add the last discarded card back so the game can progress from where it was left before the shuffling
        }

        return drawPile.popCards(n);
    }

    public void addCardsToDiscardPile(List<Card> cards) {
        discardPile.pushCards(cards);
    }

    public void addCardToDiscardPile(Card card) {
        discardPile.pushCard(card);
    }

    // create deck according to official UNO rules and shuffle it
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

    public CardPile getDrawPile() {
        return drawPile;
    }

    public CardPile getDiscardPile() {
        return discardPile;
    }

    private CardPile drawPile;
    private CardPile discardPile;

    // adds specified number of cards of each regular color
    private void addColoredCards(CardSymbol symbol, int count) {
        for (CardColor color : CardColor.values()) {
            if (color != CardColor.BLACK) {
                for (int i = 0; i < count; i++) {
                    drawPile.pushCard(new Card(color, symbol));
                }
            }
        }
    }

    // adds 4 of each wild card
    private void addWildCards() {
        for (int i = 0; i < 4; i++) {
            drawPile.pushCard(new Card(CardColor.BLACK, CardSymbol.WILD));
            drawPile.pushCard(new Card(CardColor.BLACK, CardSymbol.WILD_FOUR));
        }
    }
}
