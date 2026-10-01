package com.papermanager.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * JPanel com cantos arredondados, usado para cards de paper, chips de
 * tag/status e o campo de pesquisa. Evita duplicar logica de pintura em
 * cada componente que precisa desse visual.
 */
public class RoundedPanel extends JPanel {

    private final int radius;
    private final Color backgroundColor;
    private final Color borderColor;

    public RoundedPanel(int radius, Color backgroundColor, Color borderColor) {
        this.radius = radius;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (backgroundColor != null) {
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
