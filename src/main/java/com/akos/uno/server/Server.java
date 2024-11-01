package com.akos.uno.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.communication.action.GameAction;
import com.akos.uno.communication.action.GameActionType;
import com.akos.uno.communication.response.InvalidMoveResponse;
import com.akos.uno.game.GameController;
import com.akos.uno.game.PartialGameState;

// sources:
// - https://www.geeksforgeeks.org/multithreaded-servers-in-java/
// - https://betterstack.com/community/guides/logging/how-to-start-logging-with-log4j/
public class Server extends Thread {
    public Server(int port, CountDownLatch latch) {
        this.latch = latch;
        this.port = port;
        this.gameController = new GameController();
        gameActionHandlers.put(GameActionType.CHALLENGE_PLAYER, (action) -> {});
        gameActionHandlers.put(GameActionType.DISCARD_CARD, (action) -> {});
        gameActionHandlers.put(GameActionType.DRAW_CARD, (action) -> {});
        gameActionHandlers.put(GameActionType.JOIN, new JoinActionHandler(gameController, this));
        gameActionHandlers.put(GameActionType.JUMP_IN, (action) -> {});
        gameActionHandlers.put(GameActionType.QUIT, (action) -> {});
        gameActionHandlers.put(GameActionType.SAY_UNO, (action) -> {});
        gameActionHandlers.put(GameActionType.START, (action) -> {});
    }

    @Override
    public void run() {
        startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server is listening on port: {}", serverSocket.getLocalPort());
            latch.countDown();

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setKeepAlive(true); // keep alive connection because of the real-time nature of UNO
                logger.info("New client connected");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String playerName = in.readLine();

                clients.put(playerName, new ClientHandler(clientSocket, this));
                clients.get(playerName).start();
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

    public synchronized void broadcastGameState(String message) {
        for (ClientHandler clientHandler : clients.values()) {
            clientHandler.sendMessageToClient(message);
        }
    }

    public synchronized Map<String, ClientHandler> getClients() {
        return clients;
    }

    public synchronized GameController getGameController() {
        return gameController;
    }

    public synchronized void processMessage(String message, ClientHandler clientHandler) {
        logger.debug("Processing message: {}", message);
        GameAction action = GameAction.createFromJson(message);

        if (gameActionHandlers.containsKey(action.getType())) {
            gameActionHandlers.get(action.getType()).handleAction(action);
        } else {
            clientHandler.sendMessageToClient(new InvalidMoveResponse().getAsJson());
        }
    }

    // public static void main(String[] args) {
    //     int port = Integer.parseInt(args[0]);

    //     Server server = new Server();
    //     server.startServer(port);
    // }

    private CountDownLatch latch;
    private int port;
    private ServerSocket serverSocket;
    private HashMap<String, ClientHandler> clients = new HashMap<>();
    private GameController gameController;
    private final Map<GameActionType, GameActionHandler<?>> gameActionHandlers = new EnumMap<>(GameActionType.class);
    private Logger logger = LogManager.getLogger();
}
