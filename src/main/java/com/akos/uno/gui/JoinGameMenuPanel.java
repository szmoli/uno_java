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

public class JoinGameMenuPanel extends WindowContentPanel {
    public JoinGameMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
        getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel serverAddressLabel = new JLabel("Server address and port:");
        serverAddressInput = new JTextField(24);
        serverAddressInput.setInputVerifier(new IPInputVerifier());
        
        playerNameLabel = new JLabel("Player name:");
        playerNameInput = new JTextField(16);
        playerNameInput.setInputVerifier(new PlayerNameVerifier());
        
        joinButton = new JButton("Join Game");
        joinButton.setEnabled(false);
        joinButton.addActionListener(actionEvent -> {
            String playerName = playerNameInput.getText();
            String[] splitAddress = serverAddressInput.getText().split(":");

            // Validate input
            if (playerName.isEmpty() || splitAddress.length != 2 || !splitAddress[1].matches("\\d+")) {
                JOptionPane.showMessageDialog(frame, "Invalid player name or server address!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GamePanel gamePanel = new GamePanel(frame);
            ClientController clientController = new ClientController(playerName, splitAddress[0], Integer.parseInt(splitAddress[1]), gamePanel);
            gamePanel.setClientController(clientController);
            clientController.start();

            CountDownLatch connectionLatch = clientController.getClient().getConnectionLatch();

            // Wait for the latch with a timeout
            boolean isConnected;
            try {
                isConnected = connectionLatch.await(5, TimeUnit.SECONDS); // Wait up to 5 seconds for connection
            } catch (InterruptedException e) {
                clientController.interrupt();
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
                getFrame().addWindowListener(new CloseAdapter(clientController, getFrame()));
            });
        });

        Runnable buttonChecker = () -> {
            boolean isValid = serverAddressInput.getInputVerifier().verify(serverAddressInput) && playerNameInput.getInputVerifier().verify(playerNameInput);
            joinButton.setEnabled(isValid);
        };
        serverAddressInput.getDocument().addDocumentListener(new InputFieldListener(buttonChecker));
        playerNameInput.getDocument().addDocumentListener(new InputFieldListener(buttonChecker));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(new MainMenuPanel(frame).getPanel());
            frame.pack();
        });

        super.getPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getPanelComponents().addAll(List.of(playerNameLabel, playerNameInput, serverAddressLabel, serverAddressInput, joinButton, cancelButton));
        for (JComponent component : super.getPanelComponents()) {
            super.getPanel().add(component);
        }
    }

    private final JTextField serverAddressInput;
    private final JLabel playerNameLabel;
    private final JTextField playerNameInput;
    private final JButton joinButton;
    private final JButton cancelButton;
}
