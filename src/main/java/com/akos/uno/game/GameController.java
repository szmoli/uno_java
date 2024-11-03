package com.akos.uno.game;

import java.util.EmptyStackException;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
    public GameController() {
        this.game = new Game();
    }

    public Game getGame() {
        return game;
    }

    public Player addPlayer(Player player) {
        FullGameState gameState = game.getState();

        if (gameState.getPlayers().size() >= 10) {
            throw new IllegalStateException("Maximum player capacity reached.");
        }

        return gameState.getPlayers().put(player.getPlayerName(), player);
    }

    public Player removePlayer(Player player) {
        return game.getState().getPlayers().remove(player.getPlayerName());
    }

    public Card getTopCard() {
        try {
            return game.getState().getDeck().getDiscardPile().top();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    public boolean isActionCard(Card card) {
        return card.getSymbol() == CardSymbol.DRAW_TWO || card.getSymbol() == CardSymbol.REVERSE || card.getSymbol() == CardSymbol.SKIP || card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR;
    }

    public List<String> getOtherPlayerNames(Player excludedPlayer) {
        return getGame().getState().getPlayers().keySet().stream().filter(name -> !name.equals(excludedPlayer.getPlayerName())).collect(Collectors.toList());
    }

    public List<Integer> getOtherPlayerHandSizes(Player excludedPlayer) {
        return getGame().getState().getPlayers().values().stream().filter(p -> !p.getPlayerName().equals(excludedPlayer.getPlayerName())).map(p -> p.getHand().size()).collect(Collectors.toList());
    }

    public boolean addCardToDiscardPile(Card card) {
        if (!game.getRules().isValidMove(card)) {
            return false;
        }

        game.getState().getDeck().addCardToDiscardPile(card);
        return true;
    }

    public List<Card> drawCards(int n) {
        return game.getState().getDeck().drawCards(n);
    }

    // Selects player at currentPlayerIndex + delta
    public void selectPlayer(int delta) {
        FullGameState gameState = game.getState();
        int playerCount = gameState.getPlayers().size();
        int currentIndex = gameState.getCurrentPlayerIndex();
    
        if (!gameState.isOrderReversed()) {
            gameState.setCurrentPlayerIndex((currentIndex + delta) % playerCount);
        } else {
            gameState.setCurrentPlayerIndex((currentIndex - delta + playerCount) % playerCount);
        }
    }

    public Player getPlayerWithDelta(int delta) {
        FullGameState gameState = game.getState();
        int playerCount = gameState.getPlayers().size();
        int currentIndex = gameState.getCurrentPlayerIndex();
        String playerName;
    
        if (!gameState.isOrderReversed()) {
            playerName = gameState.getPlayerNamesInOrder().get((currentIndex + delta) % playerCount);
        } else {
            playerName = gameState.getPlayerNamesInOrder().get((currentIndex - delta + playerCount) % playerCount);
        }

        return gameState.getPlayers().get(playerName);
    }

    public void startGame() {
        FullGameState gameState = game.getState();

        // Draw a valid starting card (it can't be an action card)
        while (gameState.getDeck().getDiscardPile().top().equals(new Card(CardColor.NONE, CardSymbol.NONE)) || isActionCard(getTopCard())) {
            // Remove action card or none card from the top of the discard pile, add it back into to draw pile and shuffle it again
            Card oldCard = gameState.getDeck().getDiscardPile().popCard();
            gameState.getDeck().getDrawPile().pushCard(oldCard);
            gameState.getDeck().shuffle();

            // Draw a new card and add it to the top of the discard pile
            Card newCard = gameState.getDeck().drawCards(1).getFirst();
            gameState.getDeck().addCardToDiscardPile(newCard);
        }

        // Deal 7 cards to players
        for (Player player : gameState.getPlayers().values()) {
            player.getHand().addAll(gameState.getDeck().drawCards(7));
        }

        gameState.setGameStatus(GameStatus.IN_PROGRESS);
    }

    public boolean isPlayersTurn(Player player) {
        int currentPlayerIndex = game.getState().getCurrentPlayerIndex();
        String currentPlayerName = game.getState().getPlayerNamesInOrder().get(currentPlayerIndex);
        Player currentPlayer = game.getState().getPlayers().get(currentPlayerName);

        return player.equals(currentPlayer);
    }

    public void nextRound() {
        // todo: add logic that checks if previous player has forgotten to say uno
        selectPlayer(1);
    }

    private final Game game;
}
