package com.akos.uno.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.akos.uno.client.ClientController;

public class JoinGameMenuPanel extends WindowContentPanel {
    public JoinGameMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
        serverAddressLabel = new JLabel("Server address and port:");
        serverAddressInput = new JTextField(24);

        playerNameLabel = new JLabel("Player name:");
        playerNameInput = new JTextField(16);

        joinButton = new JButton("Join Game");
        joinButton.addActionListener(actionEvent -> {
            String playerName = playerNameInput.getText();
            String[] splitAddress = serverAddressInput.getText().split(":");
            GamePanel gamePanel = new GamePanel(frame);
            ClientController clientController = new ClientController(playerName, splitAddress[0], Integer.parseInt(splitAddress[1]), gamePanel);
            gamePanel.setClientController(clientController);
            clientController.start();
            clientController.getPlayerController().joinGame();
            frame.setContentPane(gamePanel.getPanel());
            frame.addWindowListener(new CloseAdapter(clientController, frame));
            frame.pack();
        });

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

    public JPanel getPanel() {
        return super.getPanel();
    }

    private JLabel serverAddressLabel;
    private JTextField serverAddressInput;
    private JLabel playerNameLabel;
    private JTextField playerNameInput;
    private JButton joinButton;
    private JButton cancelButton;

    private class CloseAdapter extends WindowAdapter {
        public CloseAdapter(ClientController clientController, JFrame frame) {
            this.clientController = clientController;
            this.frame = frame;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            clientController.getPlayerController().quitGame();
            frame.dispose();
            System.exit(0);
        }

        private final ClientController clientController;
        private final JFrame frame;
    }
}
