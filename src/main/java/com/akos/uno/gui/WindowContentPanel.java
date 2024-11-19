package com.akos.uno.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.akos.uno.client.ClientController;

public abstract class WindowContentPanel {
    protected WindowContentPanel(JPanel panel, JFrame frame) {
        this.panel = panel;
        this.frame = frame;
        this.clientController = null;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public JPanel getPanel() {
        return panel;
    }

    public List<JComponent> getPanelComponents() {
        return panelComponents;
    }

    public JFrame getFrame() {
        return frame;
    }

    private final JPanel panel;
    private final JFrame frame;
    private ClientController clientController;
    private final List<JComponent> panelComponents = new ArrayList<>();
}
