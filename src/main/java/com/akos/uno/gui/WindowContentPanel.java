package com.akos.uno.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class WindowContentPanel {
    public WindowContentPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public List<JComponent> getMainPanelComponents() {
        return mainPanelComponents;
    }

    private JPanel mainPanel;
    private List<JComponent> mainPanelComponents = new ArrayList<>();
}
