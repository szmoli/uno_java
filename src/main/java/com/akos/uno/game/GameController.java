package com.akos.uno.game;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameController {
    public GameController() {
        this.game = new Game();
    }

    public Player getHostPlayer() {
        return game.getState().getHostPlayer();
    }

    public Game getGame() {
        return game;
    }

    public Map<String, Player> getPlayers() {
        return game.getState().getPlayers();
    }

    public boolean addPlayer(Player player) {
        FullGameState gameState = game.getState();

        if (gameState.getPlayers().size() >= 10 || gameState.getPlayers().containsKey(player.getPlayerName())) {
            return false;
        }

        gameState.getPlayers().put(player.getPlayerName(), player);
        return true;
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

    public boolean isWildCard(Card card) {
        return card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR;
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
        while (gameState.getDeck().getDiscardPile().top().equals(new Card(CardColor.NONE, CardSymbol.NONE)) || isWildCard(getTopCard())) {
            // Remove action card or none card from the top of the discard pile, add it back into to draw pile and shuffle it again
            Card oldCard = gameState.getDeck().getDiscardPile().popCard();
            gameState.getDeck().getDrawPile().pushCard(oldCard);
            gameState.getDeck().shuffle();

            // Draw a new card and add it to the top of the discard pile and apply the effects to the first player
            Card newCard = gameState.getDeck().drawCards(1).getFirst();
            gameState.getDeck().addCardToDiscardPile(newCard);
        }

        // Deal 7 cards to players
        for (Player player : gameState.getPlayers().values()) {
            player.getHand().addAll(gameState.getDeck().drawCards(7));
        }

        applyCardEffects(getTopCard(), getPlayerWithDelta(0)); // apply effects to first player
        
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

    public void setHostPlayer(Player player) {
        game.getState().setHostPlayer(player);
    }

    public void applyCardEffects(Card card, Player player) {
        switch (card.getSymbol()) {
            case CardSymbol.DRAW_TWO:
                {
                    List<Card> drawnCards = drawCards(2);
                    player.drawCards(drawnCards);
                    selectPlayer(1);
                    break;
                }
            case CardSymbol.REVERSE:
                game.getState().reverseOrder();
                break;
            case CardSymbol.SKIP:
                selectPlayer(1);
                break;
            case CardSymbol.WILD:
                // do nothing I guess?
                // this card only changes it's color to the desired color by the player who played it
                break;
            case CardSymbol.WILD_FOUR:
                {
                    List<Card> drawnCards = drawCards(4);
                    player.drawCards(drawnCards);
                    selectPlayer(1);
                    break;
                }
            default:
                break;
        }
    }

    private final Game game;
}
