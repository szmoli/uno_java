package com.akos.uno.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class WindowContentPanel {
    public WindowContentPanel(JPanel panel, JFrame frame) {
        this.panel = panel;
        this.frame = frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public List<JComponent> getPanelComponents() {
        return panelComponents;
    }

    private JPanel panel;
    private JFrame frame;
    private List<JComponent> panelComponents = new ArrayList<>();
}
