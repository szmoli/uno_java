package com.akos.uno.gui;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.akos.uno.client.ClientController;
import com.akos.uno.server.Server;

public class HostGameMenuPanel extends WindowContentPanel {
    public HostGameMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
        getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel serverPortLabel = new JLabel("Server port:");
        serverPortInput = new JTextField(6);
        serverPortInput.setInputVerifier(new PortVerifier());
        
        JLabel playerNameLabel = new JLabel("Player name:");
        playerNameInput = new JTextField(16);
        playerNameInput.setInputVerifier(new PlayerNameVerifier());
        
        hostButton = new JButton("Host Game");
        hostButton.setEnabled(false);
        hostButton.addActionListener(actionEvent -> {
            String playerName = playerNameInput.getText();

            // Validate input
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Invalid player name or server address!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Server server = new Server(Integer.parseInt(serverPortInput.getText()));
            server.start();

            try {
                server.getReadyLatch().await();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(frame, "Failed to join the game: " + e.getMessage(), "Join Error", JOptionPane.ERROR_MESSAGE);
                frame.setContentPane(new MainMenuPanel(frame).getPanel());
                frame.pack();
                return;
            }

            GamePanel gamePanel = new GamePanel(frame);
            ClientController clientController = new ClientController(playerName, "localhost", Integer.parseInt(serverPortInput.getText()), gamePanel);
            gamePanel.setClientController(clientController);
            clientController.start();

            CountDownLatch connectionLatch = clientController.getClient().getConnectionLatch();

            // Wait for the latch with a timeout
            boolean isConnected;
            try {
                isConnected = connectionLatch.await(5, TimeUnit.SECONDS); // Wait up to 5 seconds for connection
            } catch (InterruptedException e) {
                isConnected = false; // Connection interrupted
            }

            if (!isConnected) {
                JOptionPane.showMessageDialog(frame, "Could not connect to the server. Please try again!", "Connection Failed", JOptionPane.ERROR_MESSAGE);
                frame.setContentPane(new JoinGameMenuPanel(frame).getPanel());
                frame.pack();
                return;
            }

            try {
                clientController.getPlayerController().joinGame();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Failed to join the game: " + e.getMessage(), "Join Error", JOptionPane.ERROR_MESSAGE);
                frame.setContentPane(new MainMenuPanel(frame).getPanel());
                frame.pack();
                return;
            }

            // Switch to game panel on successful connection
            SwingUtilities.invokeLater(() -> {
                frame.setContentPane(gamePanel.getPanel());
                getFrame().addWindowListener(new CloseAdapter(getClientController(), getFrame()));
            });
        });

        Runnable buttonChecker = () -> {
            boolean isValid = serverPortInput.getInputVerifier().verify(serverPortInput) && playerNameInput.getInputVerifier().verify(playerNameInput);
            hostButton.setEnabled(isValid);
        };
        serverPortInput.getDocument().addDocumentListener(new InputFieldListener(buttonChecker));
        playerNameInput.getDocument().addDocumentListener(new InputFieldListener(buttonChecker));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(new MainMenuPanel(frame).getPanel());
            frame.pack();
        });

        super.getPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getPanelComponents().addAll(List.of(playerNameLabel, playerNameInput, serverPortLabel, serverPortInput, hostButton, cancelButton));
        for (JComponent component : super.getPanelComponents()) {
            super.getPanel().add(component);
        }
    }

    public JPanel getPanel() {
        return super.getPanel();
    }

    private final JTextField serverPortInput;
    private final JTextField playerNameInput;
    private final JButton hostButton;
    private final JButton cancelButton;

    private class PortVerifier extends javax.swing.InputVerifier {
        @Override
        public boolean verify(JComponent component) {
            String str = ((JTextField) component).getText();
            // Check if string is numeric or longer than 5 digits
            if (str == null || str.isEmpty() || str.length() > 5) {
                return false;
            }
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
