package com.papermanager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Barra superior: logo a esquerda, campo de pesquisa universal e botao de
 * configuracoes a direita. Nenhum dos dois campos executa acao real ainda -
 * a pesquisa e apenas um campo de texto com um texto de apoio (placeholder).
 */
public class HeaderPanel extends JPanel {

    private static final String SEARCH_PLACEHOLDER = "Pesquisar...";

    public HeaderPanel() {
        setBackground(UITheme.BG_HEADER);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER));
        setLayout(new MigLayout("insets 0 24 0 20, fillx", "[]push[320!]16[36!]", "[]"));

        JLabel logo = new JLabel("PaperManager");
        logo.setFont(UITheme.fontLogo());
        logo.setForeground(UITheme.TEXT_PRIMARY);
        add(logo, "aligny center");

        add(buildSearchField(), "aligny center, growx, height 36!");

        add(buildIconButton("\u2699"), "aligny center, width 36!, height 36!");
    }

    private JComponent buildSearchField() {
        RoundedPanel wrapper = new RoundedPanel(18, UITheme.BG_SEARCH, null);
        wrapper.setLayout(new MigLayout("insets 0 14 0 6, fillx", "[]6[grow,fill]", "[grow,fill]"));

        JLabel icon = new JLabel("\uD83D\uDD0D");
        icon.setFont(UITheme.fontChip());
        icon.setForeground(UITheme.TEXT_MUTED);
        wrapper.add(icon, "aligny center");

        JTextField field = new JTextField(SEARCH_PLACEHOLDER);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        field.setFont(UITheme.fontPageSubtitle());
        field.setForeground(UITheme.TEXT_MUTED);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(SEARCH_PLACEHOLDER)) {
                    field.setText("");
                    field.setForeground(UITheme.TEXT_PRIMARY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(SEARCH_PLACEHOLDER);
                    field.setForeground(UITheme.TEXT_MUTED);
                }
            }
        });
        wrapper.add(field, "aligny center, growx");

        return wrapper;
    }

    private JComponent buildIconButton(String icon) {
        JButton button = new JButton(icon);
        button.setFont(UITheme.fontLogo());
        button.setForeground(UITheme.TEXT_SECONDARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(UITheme.TEXT_PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(UITheme.TEXT_SECONDARY);
            }
        });
        return button;
    }
}
