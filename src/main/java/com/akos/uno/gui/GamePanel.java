package com.akos.uno.gui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.akos.uno.game.Card;

public class GamePanel extends WindowContentPanel {
    public GamePanel(JFrame frame) {
        super(new JPanel(new BorderLayout()), frame);
        
        handPanel = new JPanel();
        tablePanel = new JPanel();
        bottomPanel = new JPanel();
        controlPanel = new JPanel();
        otherPlayersPanel = new JPanel();

        otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.PAGE_AXIS));
        handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.LINE_AXIS));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));

        JButton startGameButton = new JButton("Start");
        startGameButton.addActionListener(l -> {
            super.getClientController().getPlayerController().startGame();
        });
        controlPanel.add(startGameButton);
        
        JButton sayUnoButton = new JButton("UNO!");
        JButton challengeButton = new JButton("Challenge");
        controlPanel.add(sayUnoButton);
        controlPanel.add(challengeButton);

        bottomPanel.add(handPanel);
        bottomPanel.add(controlPanel);
        
        super.getPanel().add(otherPlayersPanel, BorderLayout.LINE_START);
        super.getPanel().add(tablePanel, BorderLayout.CENTER);
        super.getPanel().add(bottomPanel, BorderLayout.PAGE_END);
    }

    public void drawOtherPlayers(Map<String, Integer> otherPlayers) {
        otherPlayersPanel.removeAll();
        for (Entry<String, Integer> playerEntry : otherPlayers.entrySet()) {
            JPanel playerPanel = new JPanel();
            playerPanel.add(new JLabel(playerEntry.getKey() + ":"));
            playerPanel.add(new JLabel(Integer.toString(playerEntry.getValue()) + " cards"));
            otherPlayersPanel.add(playerPanel);
        }
        otherPlayersPanel.revalidate();
        otherPlayersPanel.repaint();
    }

    public void drawPlayerHand(List<Card> cards) {
        handPanel.removeAll();
        for (Card card : cards) {
            System.out.println(card.getColor() + " " + card.getSymbol());
            handPanel.add(new JLabel(card.getColor() + " " + card.getSymbol()));
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    private final JPanel otherPlayersPanel;
    private final JPanel handPanel;
    private final JPanel tablePanel;
    private final JPanel bottomPanel;
    private final JPanel controlPanel;
}
