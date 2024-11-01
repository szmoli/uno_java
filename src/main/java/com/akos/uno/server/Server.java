package com.akos.uno.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.communication.response.InvalidMoveResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.GameModel;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server {
    public Server() {
        this.gameController = new GameController(new GameModel());
        gameActionHandlers.put(GameActionType.CHALLENGE_PLAYER, (action) -> {});
        gameActionHandlers.put(GameActionType.DISCARD_CARD, (action) -> {});
        gameActionHandlers.put(GameActionType.DRAW_CARD, (action) -> {});
        gameActionHandlers.put(GameActionType.JOIN, new JoinActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.JUMP_IN, (action) -> {});
        gameActionHandlers.put(GameActionType.QUIT, (action) -> {});
        gameActionHandlers.put(GameActionType.SAY_UNO, (action) -> {});
        gameActionHandlers.put(GameActionType.START, (action) -> {});
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

        if (gameActionHandlers.containsKey(action.getType())) {
            gameActionHandlers.get(action.getType()).handleAction(action);
        } else {
            clientHandler.sendMessageToClient(new InvalidMoveResponse().getAsJson());
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);

        Server server = new Server();
        server.startServer(port);
    }

    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();
    private GameController gameController;
    private HashMap<GameActionType, GameActionHandler<?>> gameActionHandlers = new HashMap<>();
    private Logger logger = LogManager.getLogger();
}
