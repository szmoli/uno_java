package com.akos.uno.gui;

import java.awt.BorderLayout;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends WindowContentPanel {
    public GamePanel(JFrame frame) {
        super(new JPanel(new BorderLayout()), frame);
        otherPlayersPanel = new JPanel();
        otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.PAGE_AXIS));
        JPanel handPanel = new JPanel();
        super.getPanel().add(otherPlayersPanel, BorderLayout.LINE_START);
        super.getPanel().add(handPanel, BorderLayout.PAGE_END);
    }

    public void drawOtherPlayers(Map<String, Integer> otherPlayers) {
        otherPlayersPanel.removeAll();
        for (Entry<String, Integer> playerEntry : otherPlayers.entrySet()) {
            JPanel playerPanel = new JPanel();
            playerPanel.add(new JLabel(playerEntry.getKey()));
            playerPanel.add(new JLabel(Integer.toString(playerEntry.getValue())));
            otherPlayersPanel.add(playerPanel);
        }
        otherPlayersPanel.revalidate();
        otherPlayersPanel.repaint();
    }

    private final JPanel otherPlayersPanel;
}
