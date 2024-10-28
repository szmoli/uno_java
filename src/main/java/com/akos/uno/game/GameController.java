package com.akos.uno.game;

import java.util.List;

public class GameController {
    public GameController(GameModel game) {
        this.game = game;
    }

    public GameModel getGame() {
        return game;
    }

    public void initGame() {
        
    }

    public Player addPlayer(Player player) {
        FullGameState gameState = game.getGameState();

        if (gameState.getPlayers().size() >= 10) {
            throw new IllegalStateException("Maximum player capacity reached.");
        }

        return gameState.getPlayers().put(player.getPlayerName(), player);
    }

    public Player removePlayer(Player player) {
        return game.getGameState().getPlayers().remove(player.getPlayerName());
    }

    public Card getTopCard() {
        return game.getGameState().getDeck().getDiscardPile().top();
    }

    public boolean addCardToDiscardPile(Card card) {
        Card topCard = getTopCard();

        if (!game.getRules().isValidMove(card)) {
            return false;
        }

        game.getGameState().getDeck().addCardToDiscardPile(card);
        return true;
    }

    public List<Card> drawCards(int n) {
        return game.getGameState().getDeck().drawCards(n);
    }

    public void selectNextPlayer() {
        FullGameState gameState = game.getGameState();

        if (gameState.isOrderReversed()) {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size());
        }
    }

    public Player getPreviousPlayer() {
        FullGameState gameState = game.getGameState();

        if (gameState.isOrderReversed()) {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        } else {
            return gameState.getPlayers().get((gameState.getCurrentPlayerIndex() - 1) % gameState.getPlayers().size()); // modulo to wrap around the list if the index goes out of range
        }
    }

    public void startGame() {
        FullGameState gameState = game.getGameState();

        gameState.setGameStatus(GameStatus.IN_PROGRESS);

        while (gameState.getGameStatus() != GameStatus.FINISHED) {
            Player previousPlayer = getPreviousPlayer();
            Player player = gameState.getPlayers().get(gameState.getCurrentPlayerIndex());
//            player.playTurn(deck);
//            game.getRules().enforceForgotToSayUNOPenalty(previousPlayer, gameState.getDeck());
            selectNextPlayer();
        }
    }

    private GameModel game;
}