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

        if (!game.getState().getPlayers().containsKey(playerName)) {
            Player player = new Player(playerName);
            game.getState().getPlayers().put(playerName, player);
    
            for (Player p : game.getState().getPlayers().values()) {
                String message = new PartialGameStateResponse(new PartialGameState(p, game.getState())).getAsJson();
                server.getClients().get(p.getPlayerName()).sendMessageToClient(message);
            }
        }        
    }

    private GameController gameController;
    private Server server;
    private static final Logger logger = LogManager.getLogger();
}
