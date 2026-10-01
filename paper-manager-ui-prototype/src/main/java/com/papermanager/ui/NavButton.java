package com.papermanager.ui;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Item de navegacao do menu lateral. Usado tanto para os itens fixos
 * (Biblioteca, Favoritos, Lendo, Lidos) quanto para as pastas e tags
 * cadastradas, evitando duplicar o estilo visual em varios lugares.
 *
 * E um JToggleButton com pintura customizada: sem retangulo quadrado
 * padrao do Swing, com destaque arredondado quando selecionado ou em
 * hover. Nao possui nenhuma logica de negocio - apenas representa a
 * selecao visual do item.
 */
public class NavButton extends JToggleButton {

    private static final int ARC = 10;

    public NavButton(String icon, String label) {
        super(buildText(icon, label));
        setFont(UITheme.fontNavItem());
        setForeground(UITheme.TEXT_PRIMARY);
        setHorizontalAlignment(SwingConstants.LEFT);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

        addChangeListener(e -> {
            setFont(isSelected() ? UITheme.fontNavItemSelected() : UITheme.fontNavItem());
            setForeground(isSelected() ? UITheme.ACCENT_DARK : UITheme.TEXT_PRIMARY);
            repaint();
        });
    }

    private static String buildText(String icon, String label) {
        if (icon == null || icon.isEmpty()) {
            return label;
        }
        return icon + "   " + label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isSelected()) {
            g2.setColor(UITheme.ACCENT_LIGHT);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        } else if (getModel().isRollover()) {
            g2.setColor(UITheme.HOVER);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC, ARC);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
