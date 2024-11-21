package com.akos.uno.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.akos.uno.client.ClientController;
import com.akos.uno.game.Card;
import com.akos.uno.game.CardColor;
import com.akos.uno.game.CardSymbol;
import com.akos.uno.game.GameStatus;

/**
 * GamePanel for the UNO game.
 * Displays the game state.
 * Allows the player to draw, discard, challenge, and say UNO.
 * Displays the other players, the player hand, and the top card of the discard pile.
 * Displays the winner of the game.
 * Exits the application on window close.
 * Disconnects from the server on window close.
 */
public class GamePanel extends WindowContentPanel {
    /**
     * Constructor
     * @param frame The frame to display the panel in.
     */
    public GamePanel(JFrame frame) {
        super(new JPanel(new BorderLayout()), frame);
        this.hasDisplayedWinnerDialog = false;
        
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
        yourTurnLabel.setForeground(new Color(255, 0, 0));
        yourTurnLabel.setFont(new Font(yourTurnLabel.getFont().getFontName(), Font.BOLD, 24));
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

    /**
     * Draws the top card of the discard pile.
     * @param card The card to draw.
     */
    public void drawTopCard(Card card) {
        discardButton.setIcon(getCardIcon(card));
        discardButton.revalidate();
        discardButton.repaint();
    }

    /**
     * Draws the draw card button.
     */
    public void drawDrawCard() {
        for (ActionListener al : drawButton.getActionListeners()) {
            drawButton.removeActionListener(al);
        }

        drawButton.addActionListener(new DrawCardListener(getClientController()));
    }

    /**
     * Draws the other players.
     * @param otherPlayers The other players to draw.
     * @param currentPlayerName The name of the current player.
     * @param winnerName The name of the winner.
     * @param status The game status.
     */
    public void drawOtherPlayers(Map<String, Integer> otherPlayers, String currentPlayerName, String winnerName, GameStatus status) {
        otherPlayersPanel.removeAll();
        for (Entry<String, Integer> playerEntry : otherPlayers.entrySet()) {
            JLabel playerLabel = new JLabel(playerEntry.getKey() + ": " + Integer.toString(playerEntry.getValue()) + " cards");
            
            if (playerEntry.getKey().equals(currentPlayerName) && status == GameStatus.IN_PROGRESS) {
                playerLabel.setForeground(new Color(255, 0, 0));
            }

            if (playerEntry.getKey().equals(winnerName) && status == GameStatus.FINISHED) {
                playerLabel.setForeground(new Color(0, 255, 0));
            }

            otherPlayersPanel.add(playerLabel);
        }
        otherPlayersPanel.revalidate();
        otherPlayersPanel.repaint();
    }

    /**
     * Draws the winner of the game.
     * @param winnerName The name of the winner.
     * @param status The game status.
     */
    public void drawWinner(String winnerName, GameStatus status) {
        if (status != GameStatus.FINISHED || winnerName == null || hasDisplayedWinnerDialog) {
            return;
        }

        hasDisplayedWinnerDialog = true;
        
        JOptionPane.showConfirmDialog(
            getFrame(), 
            winnerName + " has won the game!", 
            "Winner", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        System.exit(0);
    }

    /**
     * Draws the turn indicator.
     * @param playerName The name of the player.
     * @param currentPlayerName The name of the current player.
     * @param winnerName The name of the winner.
     * @param status The game status.
     */
    public void drawTurnIndicator(String playerName, String currentPlayerName, String winnerName, GameStatus status) {        
        if (playerName.equals(winnerName) && status == GameStatus.FINISHED) {
            yourTurnLabel.setText("You won!");
            yourTurnLabel.setForeground(new Color(0, 255, 0));
        }

        yourTurnLabel.setVisible((playerName.equals(currentPlayerName) && status == GameStatus.IN_PROGRESS) || (playerName.equals(winnerName) && status == GameStatus.FINISHED));
    }

    /**
     * Draws the player hand.
     * @param cards The cards to draw.
     */
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
    private final JButton discardButton;
    private final JButton drawButton;
    private final JLabel yourTurnLabel;
    private static final Logger logger = LogManager.getLogger();
    private boolean hasDisplayedWinnerDialog;

    /**
     * Gets the icon for a card.
     * @param card The card to get the icon for.
     * @return The icon for the card.
     */
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
        int width;
        int height;
        if (originalImage != null) {
            width = (int) Math.round(originalImage.getWidth() * 0.5);
            height = (int) Math.round(originalImage.getHeight() * 0.5);
            resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_FAST);
        }

        return new ImageIcon(resizedImage);
    }

    /**
     * Creates a card button.
     * @param card The card to create the button for.
     * @param actionListener The action listener for the button.
     * @return The created button.
     */
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

    /**
     * ActionListener for drawing a card.
     */
    private class DrawCardListener implements ActionListener {
        /**
         * Constructor
         * @param clientController The client controller.
         */
        public DrawCardListener(ClientController clientController) {
            this.clientController = clientController;
        }

        /**
         * Draws a card.
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            clientController.getPlayerController().drawCards(1);
        }

        private final ClientController clientController;
    }

    /**
     * ActionListener for discarding a card.
     */
    private class DiscardCardListener implements ActionListener {
        /**
         * Constructor
         * @param card The card to discard.
         * @param clientController The client controller.
         * @param frame The frame.
         */
        public DiscardCardListener(Card card, ClientController clientController, JFrame frame) {
            this.card = card;
            this.clientController = clientController;
            this.frame = frame;
        }

        /**
         * Discards a card.
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            CardColor desiredColor = CardColor.NONE;

            if (card.getColor() == CardColor.WILD) {
                desiredColor = showColorSelectionDialog(frame);
            }

            clientController.getPlayerController().discardCard(new Card(card.getColor(), card.getSymbol()), desiredColor);
        }

        private final Card card;
        private final ClientController clientController;
        private final JFrame frame;

        /**
         * Shows a color selection dialog.
         * @param frame The frame to display the dialog in.
         * @return The selected color.
         */
        private CardColor showColorSelectionDialog(JFrame frame) {
            JDialog dialog = new JDialog(getFrame(), "Select a color!", true);
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
    
            return selectedColor[0];
        }
    }
}
