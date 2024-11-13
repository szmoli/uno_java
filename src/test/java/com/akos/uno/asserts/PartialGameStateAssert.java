package com.akos.uno.asserts;

import java.util.List;

import com.akos.uno.game.Card;
import com.akos.uno.game.GameStatus;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class PartialGameStateAssert {
    public PartialGameStateAssert(PartialGameState state) {
        this.state = state;
    }

    public static PartialGameStateAssert assertThat(PartialGameState state) {
        return new PartialGameStateAssert(state);
    }

    public PartialGameStateAssert hasPlayer(Player expected) {
        if (!state.getPlayer().equals(expected)) {
            throw new AssertionError("Expected player: " + expected + ", actual: " + state.getPlayer());
        }
        return this;
    }

    public PartialGameStateAssert hasOtherPlayerNames(List<String> expected) {
        if (!state.getOtherPlayerNames().equals(expected)) {
            throw new AssertionError("Expected other player names: " + expected + ", actual: " + state.getOtherPlayerNames());
        }
        return this;
    }

    public PartialGameStateAssert hasOtherPlayerHandSizes(List<Integer> expected) {
        if (!state.getOtherPlayerHandSizes().equals(expected)) {
            throw new AssertionError("Expected other player hand sizes: " + expected + ", actual: " + state.getOtherPlayerHandSizes());
        }
        return this;
    }

    public PartialGameStateAssert hasTopCard(Card expected) {
        Card topCard = state.getTopCard();
        if (!topCard.equals(expected)) {
            throw new AssertionError("Expected top card: " + expected + ", actual: " + state.getTopCard());
        }
        return this;
    }

    public PartialGameStateAssert hasGameStatus(GameStatus expected) {
        if (!state.getGameStatus().equals(expected)) {
            throw new AssertionError("Expected game status: " + expected + ", actual: " + state.getGameStatus());
        }
        return this;
    }

    private PartialGameState state;
}
