package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.JoinAction;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.Game;
import com.akos.uno.game.GameController;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;

public class JoinActionHandler implements GameActionHandler<JoinAction> {
    public JoinActionHandler(GameController gameController, Server server) {
        this.gameController = gameController;
        this.server = server;
    }

    @Override
    public void handle(JoinAction action) {
        logger.debug("Handling join action: {}", action.getAsJson());
        Game game = gameController.getGame();
        String playerName = action.getPlayerName();
        Player player = new Player(playerName);
        game.getState().getPlayers().put(playerName, player);

        for (Player p : game.getState().getPlayers().values()) {
            String message = new PartialGameStateResponse(new PartialGameState(p, game.getState())).getAsJson();
            server.getClients().get(p.getPlayerName()).sendMessageToClient(message);
        }

        // server.broadcastMessage(new PartialGameStateResponse(new PartialGameState(player, game.getState())).getAsJson());
    }

    private GameController gameController;
    private Server server;
    private static final Logger logger = LogManager.getLogger();
}
