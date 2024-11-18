package com.akos.uno.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.game.Card;
import com.akos.uno.game.CardSymbol;

public class GamePanel extends WindowContentPanel {
    public GamePanel(JFrame frame) {
        super(new JPanel(new BorderLayout()), frame);
        frame.setSize(1600, 1000);
        
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

            String symbol = card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR ? "" : card.getColor().toString().toLowerCase() + "_";
            String fileName = symbol + card.getSymbol().toString().toLowerCase() + ".png";
            System.out.println(fileName);
            
            BufferedImage originalImage = null;
            try {
                originalImage = ImageIO.read(MainWindow.class.getResource("/imgs/" + fileName));
            } catch (IOException ex) {
                logger.error(ex);
            }
            
            
            Image resizedImage = null;
            int width = 0;
            int height = 0;
            if (originalImage != null) {
                width = (int) Math.round(originalImage.getWidth() * 0.4);
                height = (int) Math.round(originalImage.getHeight() * 0.4);
                resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_FAST);
            }
            Icon icon = new ImageIcon(resizedImage);
            JButton cardButton = new JButton(icon);

            cardButton.setSize(width, height);
            handPanel.add(cardButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    private final JPanel otherPlayersPanel;
    private final JPanel handPanel;
    private final JPanel tablePanel;
    private final JPanel bottomPanel;
    private final JPanel controlPanel;
    private static final Logger logger = LogManager.getLogger();
}
