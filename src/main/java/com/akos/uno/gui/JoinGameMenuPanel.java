package com.akos.uno.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JoinGameMenuPanel extends WindowContentPanel {
    public JoinGameMenuPanel(JFrame frame) {
        super(new JPanel(), frame);
        serverAddressLabel = new JLabel("Server address and port:");
        serverAddressInput = new JTextField();
        serverAddressInput.setPreferredSize(new Dimension(200, 24));

        joinButton = new JButton("Join Game");
        joinButton.addActionListener(actionEvent -> {
            // todo: start client and join server and open game window
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(actionEvent -> {
            frame.setContentPane(new MainMenuPanel(frame).getPanel());
            frame.pack();
        });

        super.getPanel().setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        super.getPanelComponents().addAll(List.of(serverAddressLabel, serverAddressInput, joinButton, cancelButton));
        for (JComponent component : super.getPanelComponents()) {
            super.getPanel().add(component);
        }
    }

    public JPanel getPanel() {
        return super.getPanel();
    }

    private JLabel serverAddressLabel;
    private JTextField serverAddressInput;
    private JButton joinButton;
    private JButton cancelButton;
}
