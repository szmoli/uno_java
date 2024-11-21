package com.akos.uno.game;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for controlling the game logic.
 */
public class GameController {
    /**
     * Constructs a new GameController instance.
     */
    public GameController() {
        this.game = new Game();
    }

    /**
     * Returns the host player of the game.
     * @return The host player of the game
     */
    public Player getHostPlayer() {
        return game.getState().getHostPlayer();
    }

    /**
     * Returns the current game.
     * @return The current game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns the players in the game.
     * @return The players in the game
     */
    public Map<String, Player> getPlayers() {
        return game.getState().getPlayers();
    }

    /**
     * Adds a player to the game.
     * @param player The player to add
     * @return True if the player was added, false otherwise
     */
    public boolean addPlayer(Player player) {
        FullGameState gameState = game.getState();

        if (gameState.getPlayers().size() >= 10 || gameState.getPlayers().containsKey(player.getPlayerName())) {
            return false;
        }

        gameState.getPlayers().put(player.getPlayerName(), player);
        return true;
    }

    /**
     * Removes a player from the game.
     * @param player The player to remove
     * @return The removed player
     */
    public Player removePlayer(Player player) {
        return game.getState().getPlayers().remove(player.getPlayerName());
    }

    /**
     * Returns the top card of the discard pile.
     * @return The top card of the discard pile
     */
    public Card getTopCard() {
        try {
            return game.getState().getDeck().getDiscardPile().top();
        } catch (EmptyStackException e) {
            return new Card(CardColor.NONE, CardSymbol.NONE);
        }
    }

    /**
     * Checks if the card is a wild card.
     * @param card The card to check
     * @return True if the card is a wild card, false otherwise
     */
    public boolean isWildCard(Card card) {
        return card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR;
    }

    /**
     * Returns the names of the other players in the game.
     * @param excludedPlayer The player to exclude
     * @return The names of the other players in the game
     */
    public List<String> getOtherPlayerNames(Player excludedPlayer) {
        return getGame().getState().getPlayers().keySet().stream().filter(name -> !name.equals(excludedPlayer.getPlayerName())).collect(Collectors.toList());
    }

    /**
     * Returns the hand sizes of the other players in the game.
     * @param excludedPlayer The player to exclude
     * @return The hand sizes of the other players in the game
     */
    public List<Integer> getOtherPlayerHandSizes(Player excludedPlayer) {
        return getGame().getState().getPlayers().values().stream().filter(p -> !p.getPlayerName().equals(excludedPlayer.getPlayerName())).map(p -> p.getHand().size()).collect(Collectors.toList());
    }

    /**
     * Adds a card to the discard pile.
     * @param card The card to add
     * @return True if the card was added, false otherwise
     */
    public boolean addCardToDiscardPile(Card card) {
        if (!game.getRules().isValidMove(card, getTopCard())) {
            return false;
        }

        game.getState().getDeck().addCardToDiscardPile(card);
        return true;
    }

    /**
     * Draws n cards from the draw pile.
     * @param n The number of cards to draw
     * @return The drawn cards
     */
    public List<Card> drawCards(int n) {
        return game.getState().getDeck().drawCards(n);
    }

    /**
     * Selects player with the specified offset from the current player.
     * Takes order of play into account.
     * @param delta The offset from the current player
     */
    public void selectPlayerWithDelta(int delta) {
        FullGameState gameState = game.getState();
        int playerCount = gameState.getPlayers().size();
        int currentIndex = gameState.getCurrentPlayerIndex();
    
        if (!gameState.isOrderReversed()) {
            gameState.setCurrentPlayerIndex((currentIndex + delta) % playerCount);
        } else {
            gameState.setCurrentPlayerIndex((currentIndex - delta + playerCount) % playerCount);
        }
    }

    /**
     * Returns the player with the specified offset from the current player.
     * Takes order of play into account.
     * @param delta The offset from the current player
     * @return The player with the specified offset from the current player
     */
    public Player getPlayerWithDelta(int delta) {
        FullGameState gameState = game.getState();
        int playerCount = gameState.getPlayers().size();
        int currentIndex = gameState.getCurrentPlayerIndex();
        String playerName;

        if (delta < 0) {
            delta = delta + playerCount;
        }
    
        if (!gameState.isOrderReversed()) {
            playerName = gameState.getPlayerNamesInOrder().get((currentIndex + delta) % playerCount);
        } else {
            playerName = gameState.getPlayerNamesInOrder().get((currentIndex - delta + playerCount) % playerCount);
        }

        return gameState.getPlayers().get(playerName);
    }

    /**
     * Checks if the card is an effect card.
     * @param card The card to check
     * @return True if the card is an effect card, false otherwise
     */
    public boolean isEffectCard(Card card) {
        return card.getSymbol() == CardSymbol.DRAW_TWO || card.getSymbol() == CardSymbol.REVERSE || card.getSymbol() == CardSymbol.SKIP || card.getSymbol() == CardSymbol.WILD_FOUR;
    }

    /**
     * Starts the game.
     * Deals 7 cards to each player and draws a valid starting card. If the starting card is a wild card, it is replaced with a new card.
     * The game status is set to IN_PROGRESS.
     * The first player is selected and the effects of the starting card are applied.
     */
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

    /**
     * Checks if it is the player's turn.
     * @param player The player to check
     * @return True if it is the player's turn, false otherwise
     */
    public boolean isPlayersTurn(Player player) {
        int currentPlayerIndex = game.getState().getCurrentPlayerIndex();
        String currentPlayerName = game.getState().getPlayerNamesInOrder().get(currentPlayerIndex);
        Player currentPlayer = game.getState().getPlayers().get(currentPlayerName);

        return player.equals(currentPlayer);
    }

    /**
     * Moves to the next round.
     * If the player has won the game, the game status is set to FINISHED.
     * If the previous player has forgotten to say UNO, they draw 2 cards.
     * The next player is selected and the UNO status of the current player is reset.
     */
    public void nextRound() {
        // player wins game
        if (getPlayerWithDelta(0).getHand().isEmpty()) {
            logger.debug("{} has won the game", getPlayerWithDelta(0).getPlayerName());
            getGame().getState().setWinner(getPlayerWithDelta(0));
            getGame().getState().setGameStatus(GameStatus.FINISHED);
        }

        // check if previous player has forgotten to say uno and enforce the penalty
        if (getPlayerWithDelta(-1).getHand().size() == 1 && !getPlayerWithDelta(-1).hasSaidUno()) {
            logger.debug("{} forgot to say UNO", getPlayerWithDelta(-1).getPlayerName());
            getPlayerWithDelta(-1).drawCards(drawCards(2));
        }
        
        selectPlayerWithDelta(1);
        getPlayerWithDelta(0).setHasSaidUno(false); // todo: check if this works as intended
    }

    /**
     * Sets the host player of the game.
     * @param player The host player of the game
     */
    public void setHostPlayer(Player player) {
        game.getState().setHostPlayer(player);
    }

    /**
     * Reverses the order of play.
     */
    public void reverseOrder() {
        game.getState().reverseOrder();
    }

    /**
     * Applies the effects of the card to the player.
     * @param card The card to apply effects of
     * @param player The player to apply effects to
     */
    public void applyCardEffects(Card card, Player player) {
        logger.debug("Card effects applied: {}", card.getSymbol());

        switch (card.getSymbol()) {
            case CardSymbol.DRAW_TWO -> {
                List<Card> drawnCards = drawCards(2);
                player.drawCards(drawnCards);
                selectPlayerWithDelta(1);
            }
            case CardSymbol.REVERSE -> reverseOrder();
            case CardSymbol.SKIP -> nextRound();
            case CardSymbol.WILD_FOUR -> {
                List<Card> drawnCards = drawCards(4);
                player.drawCards(drawnCards);
                selectPlayerWithDelta(1);
            }
            default -> {}
        }
    }

    /**
     * Shuffles the draw pile.
     */
    public void shuffleDrawPile() {
        game.getState().getDeck().shuffle();
    }

    /**
     * Returns the second card in the discard pile.
     */
    public Card getSecondCard() {
        try {
            return game.getState().getDeck().getDiscardPile().getSecondCard();
        } catch (EmptyStackException e) {
            return new Card(CardColor.NONE, CardSymbol.NONE);
        }
    }

    private final Game game;
    private static final Logger logger = LogManager.getLogger();
}
