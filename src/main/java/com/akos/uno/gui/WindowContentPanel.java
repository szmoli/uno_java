package com.akos.uno.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.akos.uno.client.ClientController;

/**
 * WindowContentPanel
 * Abstract class for window content panels.
 * Contains a panel, frame, and client controller.
 */
public abstract class WindowContentPanel {
    /**
     * WindowContentPanel constructor
     * @param panel The panel to display
     * @param frame The frame to display the panel in
     */
    protected WindowContentPanel(JPanel panel, JFrame frame) {
        this.panel = panel;
        this.frame = frame;
        this.clientController = null;
    }

    /**
     * Set the client controller
     * @param clientController The client controller
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    /**
     * Get the client controller
     * @return The client controller
     */
    public ClientController getClientController() {
        return clientController;
    }

    /**
     * Get the panel
     * @return The panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Get the panel components
     * @return The panel components
     */
    public List<JComponent> getPanelComponents() {
        return panelComponents;
    }

    /**
     * Get the frame
     * @return The frame
     */
    public JFrame getFrame() {
        return frame;
    }

    private final JPanel panel;
    private final JFrame frame;
    private ClientController clientController;
    private final List<JComponent> panelComponents = new ArrayList<>();
}
