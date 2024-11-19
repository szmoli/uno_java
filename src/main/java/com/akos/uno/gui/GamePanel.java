package com.akos.uno.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.client.ClientController;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.CardSymbol;

public class GamePanel extends WindowContentPanel {
    public GamePanel(JFrame frame) {
        super(new JPanel(new BorderLayout()), frame);
        
        // handPanel = new JPanel();
        // tablePanel = new JPanel();
        // JPanel yourTurnPanel = new JPanel();
        // bottomPanel = new JPanel();
        // controlPanel = new JPanel();
        // otherPlayersPanel = new JPanel();
        // discardButton = createCardButton(new Card(CardColor.NONE, CardSymbol.NONE), null);
        // drawButton = createCardButton(new Card(CardColor.NONE, CardSymbol.NONE), null);

        // otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.PAGE_AXIS));
        // handPanel.setLayout(new BoxLayout(handPanel, BoxLayout.LINE_AXIS));
        // controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        // tablePanel.setLayout(new GridLayout(2, 2));

        // yourTurnLabel = new JLabel("It's your turn!");
        // // yourTurnLabel.setVisible(false);

        // yourTurnPanel.add(yourTurnLabel);
        // tablePanel.add(discardButton);
        // tablePanel.add(drawButton);
        // JLabel discardLabel = new JLabel("Discard", SwingConstants.CENTER);
        // JLabel drawLabel = new JLabel("Draw", SwingConstants.CENTER);
        // tablePanel.add(discardLabel);
        // tablePanel.add(drawLabel);

        // JButton startGameButton = new JButton("Start");
        // startGameButton.addActionListener(l -> {
        //     getClientController().getPlayerController().startGame();
        // });
        // controlPanel.add(startGameButton);
        
        // JButton sayUnoButton = new JButton("UNO!");
        // JButton challengeButton = new JButton("Challenge");
        // controlPanel.add(sayUnoButton);
        // controlPanel.add(challengeButton);

        // bottomPanel.add(handPanel);
        // bottomPanel.add(controlPanel);
        
        // super.getPanel().add(otherPlayersPanel, BorderLayout.LINE_START);
        // super.getPanel().add(tablePanel, BorderLayout.CENTER);
        // super.getPanel().add(bottomPanel, BorderLayout.PAGE_END);

        // Frame
        getFrame().setSize(1200, 800);

        // Content panel
        getPanel().setLayout(new BorderLayout());
        getPanel().setPreferredSize(new Dimension(1200, 800));

        // Your turn label
        yourTurnLabel = new JLabel("It's your turn!");

        // Top panel: turn indicator
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1200, 50));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(yourTurnLabel);
        getPanel().add(topPanel, BorderLayout.NORTH);

        // Left panel: other players
        otherPlayersPanel = new JPanel();
        otherPlayersPanel.setPreferredSize(new Dimension(150, 800));
        otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.Y_AXIS));
        getPanel().add(otherPlayersPanel, BorderLayout.WEST);

        // Center panel: card piles
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        discardButton = createCardButton(new Card(CardColor.NONE, CardSymbol.NONE), null);
        discardButton.setText("Discard");
        drawButton = createCardButton(new Card(CardColor.NONE, CardSymbol.NONE), null);
        drawButton.setText("Draw");
        centerPanel.add(discardButton);
        centerPanel.add(drawButton);
        getPanel().add(centerPanel, BorderLayout.CENTER);

        // Bottom panel: player hand, control buttons
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Hand panel
        JScrollPane handScrollPane = new JScrollPane();
        handPanel = new JPanel();
        handPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        handScrollPane.setViewportView(handPanel);
        handScrollPane.setPreferredSize(new Dimension(1000, 200));
        bottomPanel.add(handScrollPane, BorderLayout.CENTER);

        // Control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(200, 200));
        JButton startButton = new JButton("Start");
        startButton.addActionListener(l -> getClientController().getPlayerController().startGame());
        JButton challengeButton = new JButton("Challenge");
        challengeButton.addActionListener(l -> getClientController().getPlayerController().challengePlayer());
        JButton unoButton = new JButton("UNO");
        unoButton.addActionListener(l -> getClientController().getPlayerController().sayUno());
        controlPanel.add(startButton);
        controlPanel.add(unoButton);
        controlPanel.add(challengeButton);
        bottomPanel.add(controlPanel, BorderLayout.EAST);

        getPanel().add(bottomPanel, BorderLayout.SOUTH);
    }

    public void drawTopCard(Card card) {
        discardButton.setIcon(getCardIcon(card));
        discardButton.revalidate();
        discardButton.repaint();
    }

    public void drawDrawCard() {
        for (ActionListener al : drawButton.getActionListeners()) {
            drawButton.removeActionListener(al);
        }

        drawButton.addActionListener(new DrawCardListener(getClientController()));
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
            handPanel.add(createCardButton(card, new DiscardCardListener(card, getClientController(), getFrame())));
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    private final JPanel otherPlayersPanel;
    private final JPanel handPanel;
    private final JPanel bottomPanel;
    private final JPanel controlPanel;
    private JButton discardButton;
    private final JButton drawButton;
    private final JLabel yourTurnLabel;
    private static final Logger logger = LogManager.getLogger();

    private Icon getCardIcon(Card card) {
        String symbol = card.getColor() == CardColor.NONE || card.getColor() == CardColor.WILD || card.getSymbol() == CardSymbol.WILD || card.getSymbol() == CardSymbol.WILD_FOUR ? "" : card.getColor().toString().toLowerCase() + "_";
        String fileName = symbol + card.getSymbol().toString().toLowerCase() + ".png";
        
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

        return new ImageIcon(resizedImage);
    }

    private JButton createCardButton(Card card, ActionListener actionListener) {
        JButton cardButton = new JButton(getCardIcon(card));
        cardButton.addActionListener(actionListener);
        cardButton.setSize(cardButton.getIcon().getIconWidth(), cardButton.getIcon().getIconHeight());
        cardButton.setBorderPainted(false);
        cardButton.setContentAreaFilled(false);
        cardButton.setFocusPainted(false);
        cardButton.setOpaque(false);
        return cardButton;
    }

    private class DrawCardListener implements ActionListener {
        public DrawCardListener(ClientController clientController) {
            this.clientController = clientController;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            clientController.getPlayerController().drawCards(1);
        }

        private final ClientController clientController;
    }

    private class DiscardCardListener implements ActionListener {
        public DiscardCardListener(Card card, ClientController clientController, JFrame frame) {
            this.card = card;
            this.clientController = clientController;
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardColor desiredColor = CardColor.NONE;

            if (card.getColor() == CardColor.WILD) {
                desiredColor = showColorSelectionDialog(frame);
            }

            clientController.getPlayerController().discardCard(new Card(card.getColor(), card.getSymbol()), desiredColor);
            System.out.println(desiredColor);
        }

        private final Card card;
        private final ClientController clientController;
        private final JFrame frame;

        private CardColor showColorSelectionDialog(JFrame frame) {
            JDialog dialog = new JDialog(frame, "Select a color!", true);
            dialog.setLayout(new FlowLayout());
            dialog.setSize(250, 150);
            dialog.add(new JLabel("Select a color:"));
    
            JComboBox<CardColor> colorsBox = new JComboBox<>(new CardColor[]{CardColor.RED, CardColor.YELLOW, CardColor.GREEN, CardColor.BLUE});
            dialog.add(colorsBox);
            
            JButton okButton = new JButton("Select");
            final CardColor[] selectedColor = { null };
    
            okButton.addActionListener(l -> {
                selectedColor[0] = (CardColor) colorsBox.getSelectedItem();
                dialog.dispose();
            });
    
            dialog.add(okButton);
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);

            System.out.println(selectedColor[0].toString());
    
            return selectedColor[0];
        }
    }
}
