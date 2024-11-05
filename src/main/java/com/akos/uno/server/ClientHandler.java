package com.akos.uno.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// sources:
// - https://www.w3schools.com/java/java_threads.asp
public class ClientHandler extends Thread {
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            logger.error("Error starting client thread: {}", e.getMessage());
            
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e2) {
                logger.error("Error closing client: {}", e2.getMessage());
            }            
        }        
    }

    public void sendMessageToClient(String message) {
        out.println(message);
        logger.debug("Sent response to client: {}", message);
    }

    // creates a new thread for the client
    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                server.processMessage(message, this);
            }
        } catch (IOException e) {
            logger.error("Error starting client thread: {}", e.getMessage());
            
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e2) {
                logger.error("Error closing client: {}", e2.getMessage());
            }            
        }
    }

    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Logger logger = LogManager.getLogger();
    private Server server;
}
