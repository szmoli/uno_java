package com.akos.uno.gui;

import javax.swing.*;

public class PlayerComponent {
    public PlayerComponent(String playerName, int handSize) {
        panel = new JPanel();
    }

    public void setHandSizeLabelText(int size) {
        handSizeLabel.setText("Hand: " + Integer.toString(size));
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel panel;
    private JLabel playerNameLabel;
    private JLabel handSizeLabel;
}
