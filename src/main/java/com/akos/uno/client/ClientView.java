package com.akos.uno.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.PartialGameState;
import com.akos.uno.gui.GamePanel;

public class ClientView {
    public ClientView(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void updateView(PartialGameState state) {
        logger.debug("Updating client view with: ");
        Map<String, Integer> otherPlayers = new HashMap<>();
        for (int i = 0; i < state.getOtherPlayerNames().size(); i++) {
            otherPlayers.put(state.getOtherPlayerNames().get(i), state.getOtherPlayerHandSizes().get(i));
            logger.debug(state.getOtherPlayerNames().get(i));
            logger.debug(state.getOtherPlayerHandSizes().get(i));
        }

        gamePanel.drawOtherPlayers(otherPlayers, state.getCurrentPlayerName(), state.getWinnerName(), state.getGameStatus());
        gamePanel.drawPlayerHand((state.getPlayer().getHand()));
        gamePanel.drawTopCard(state.getTopCard());
        gamePanel.drawDrawCard();
        gamePanel.drawTurnIndicator(state.getPlayer().getPlayerName(), state.getCurrentPlayerName(), state.getWinnerName(), state.getGameStatus());
        gamePanel.drawWinner(state.getWinnerName(), state.getGameStatus());
    }

    private final GamePanel gamePanel;
    private static final Logger logger = LogManager.getLogger();
}
