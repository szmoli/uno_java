package com.akos.uno.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.communication.response.InvalidMoveResponse;
import com.akos.uno.communication.response.PartialGameStateResponse;
import com.akos.uno.game.GameModel;
import com.akos.uno.game.GameRules;
import com.akos.uno.game.PartialGameState;
import com.akos.uno.game.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server {
    public Server(GameRules rules) {
        this.game = new GameModel(rules);
        operations.put(GameActionType.CHALLENGE_PLAYER, (action) -> {});
        operations.put(GameActionType.DISCARD_CARD, (action) -> {});
        operations.put(GameActionType.DRAW_CARD, (action) -> {});
        operations.put(GameActionType.JOIN, (action) -> {
            String playerName = action.getPlayerName();
            Player player = new Player(playerName);
            game.getGameState().getPlayers().put(playerName, player);
            broadcastMessage(new PartialGameStateResponse(new PartialGameState(player, game.getGameState())).getAsJson());
        });
        operations.put(GameActionType.JUMP_IN, (action) -> {});
        operations.put(GameActionType.QUIT, (action) -> {});
        operations.put(GameActionType.SAY_UNO, (action) -> {});
        operations.put(GameActionType.START, (action) -> {});
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server is listening on port: {}", serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setKeepAlive(true); // keep alive connection because of the real-time nature of UNO
                logger.info("New client connected");

                clients.add(new ClientHandler(clientSocket, this));
                clients.getLast().start();
            }
        } catch (IOException e) {
            logger.error("Error starting server: {}", e.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logger.error("Error stopping server: {}", e.getMessage());
        }
    }

    public synchronized void sendMessageToClient(ClientHandler clientHandler, String message) {
        clientHandler.sendMessageToClient(message);
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessageToClient(message);
        }
    }

    public synchronized List<ClientHandler> getClients() {
        return clients;
    }

    public synchronized void processMessage(String message, ClientHandler clientHandler) {
        logger.debug("Processing message: " + message);
        GameAction action = GameAction.createFromJson(message);

        if (operations.containsKey(action.getType())) {
            operations.get(action.getType()).execute(action);
        } else {
            clientHandler.sendMessageToClient(new InvalidMoveResponse().getAsJson());
        }
    }

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private GameModel game;
    private HashMap<GameActionType, ActionOperation> operations = new HashMap<>();
    private Logger logger = LogManager.getLogger();
}
