package com.akos.uno.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// sources:
// - https://www.w3schools.com/java/java_threads.asp
public class ClientHandler extends Thread {
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    // creates a new thread for the client
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                String response = processMessage(message);
                sendResponse(message);
            }
        } catch (IOException e) {
            logger.error("Error starting client thread: {}", e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                logger.error("Error closing client thread: {}", e.getMessage());
            }
        }
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Logger logger = LogManager.getLogger();

    private String processMessage(String message) {
        logger.debug("Processed message from client: {}", message);
        return message;
    }

    private void sendResponse(String message) {
        out.println(message);
        logger.debug("Sent response to client: {}", message);
    }
}
