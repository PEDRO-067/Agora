package com.papermanager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;

/**
 * Menu lateral fixo. Contem os atalhos fixos (Biblioteca, Favoritos,
 * Lendo, Lidos), a lista de pastas (nivel unico, sem subpastas), a lista
 * de tags e as acoes de adicionar paper / nova pasta.
 *
 * Todos os itens de navegacao compartilham um unico ButtonGroup, entao
 * apenas um fica selecionado por vez - isso e so visual, nenhum deles
 * troca o conteudo real da tela ainda.
 */
public class SidebarPanel extends JPanel {

    private static final String[] FOLDERS = {
            "Intelig\u00eancia Artificial", "Redes", "Banco de Dados", "TCC"
    };

    private static final String[] TAGS = {
            "#MachineLearning", "#DeepLearning", "#Redes", "#XGBoost", "#Transformers"
    };

    public SidebarPanel() {
        setBackground(UITheme.BG_SIDEBAR);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, UITheme.BORDER));
        setLayout(new MigLayout("wrap, insets 16 10 16 10, gapy 2, fillx", "[grow,fill]", ""));

        ButtonGroup navGroup = new ButtonGroup();

        NavButton biblioteca = new NavButton("\uD83D\uDCDA", "Biblioteca");
        NavButton favoritos = new NavButton("\u2B50", "Favoritos");
        NavButton lendo = new NavButton("\uD83D\uDCD6", "Lendo");
        NavButton lidos = new NavButton("\u2713", "Lidos");

        for (NavButton item : new NavButton[]{biblioteca, favoritos, lendo, lidos}) {
            navGroup.add(item);
            add(item, "growx, height 38!");
        }
        biblioteca.setSelected(true);

        add(sectionLabel("PASTAS"), "growx, gaptop 18, gapbottom 2");
        for (String folder : FOLDERS) {
            NavButton folderButton = new NavButton("\uD83D\uDCC1", folder);
            navGroup.add(folderButton);
            add(folderButton, "growx, height 34!, gapleft 4");
        }

        add(sectionLabel("TAGS"), "growx, gaptop 18, gapbottom 2");
        for (String tag : TAGS) {
            NavButton tagButton = new NavButton("", tag);
            navGroup.add(tagButton);
            add(tagButton, "growx, height 32!, gapleft 4");
        }

        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, "growy, pushy, wrap");

        add(buildSeparator(), "growx, gaptop 4, gapbottom 10");

        JButton addPaper = buildPrimaryButton("\uFF0B  Adicionar Paper");
        add(addPaper, "growx, height 38!");

        JButton addFolder = buildSecondaryButton("\uFF0B  Nova Pasta");
        add(addFolder, "growx, height 34!, gaptop 6");
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.fontSectionLabel());
        label.setForeground(UITheme.TEXT_MUTED);
        label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        return label;
    }

    private JComponent buildSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(UITheme.BORDER);
        return separator;
    }

    private JButton buildPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.fontButton());
        button.setForeground(Color.WHITE);
        button.setBackground(UITheme.ACCENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton buildSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UITheme.fontButton());
        button.setForeground(UITheme.TEXT_PRIMARY);
        button.setBackground(UITheme.BG_SIDEBAR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(UITheme.BORDER));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
