package com.papermanager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * Card visual de um paper fict\u00edcio: t\u00edtulo, autores/ano, tags e status
 * de leitura. Apenas representa\u00e7\u00e3o visual - sem liga\u00e7\u00e3o com model,
 * banco de dados ou qualquer dado real.
 */
public class PaperCard extends RoundedPanel {

    public enum Status {
        LIDO("Lido", UITheme.STATUS_LIDO_BG, UITheme.STATUS_LIDO_FG),
        LENDO("Lendo", UITheme.STATUS_LENDO_BG, UITheme.STATUS_LENDO_FG),
        NAO_LIDO("N\u00e3o lido", UITheme.STATUS_NAO_LIDO_BG, UITheme.STATUS_NAO_LIDO_FG);

        private final String label;
        private final Color background;
        private final Color foreground;

        Status(String label, Color background, Color foreground) {
            this.label = label;
            this.background = background;
            this.foreground = foreground;
        }
    }

    public PaperCard(String title, String authors, int year, String[] tags, Status status) {
        super(12, UITheme.BG_CARD, UITheme.BORDER_CARD);
        setLayout(new MigLayout("insets 16 18 16 18, wrap, fillx, gapy 6", "[grow,fill]", ""));

        JTextArea titleArea = new JTextArea(title);
        titleArea.setFont(UITheme.fontCardTitle());
        titleArea.setForeground(UITheme.TEXT_PRIMARY);
        titleArea.setLineWrap(true);
        titleArea.setWrapStyleWord(true);
        titleArea.setEditable(false);
        titleArea.setFocusable(false);
        titleArea.setOpaque(false);
        titleArea.setBorder(null);
        add(titleArea, "growx, wmin 0");

        JLabel metaLabel = new JLabel(authors + "  \u2022  " + year);
        metaLabel.setFont(UITheme.fontCardMeta());
        metaLabel.setForeground(UITheme.TEXT_SECONDARY);
        add(metaLabel, "growx");

        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setOpaque(false);

        JPanel tagsFlow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        tagsFlow.setOpaque(false);
        for (String tag : tags) {
            tagsFlow.add(buildTagChip(tag));
        }
        bottomRow.add(tagsFlow, BorderLayout.WEST);
        bottomRow.add(buildStatusBadge(status), BorderLayout.EAST);

        add(bottomRow, "growx, gaptop 4");
    }

    private JComponent buildTagChip(String text) {
        RoundedPanel chip = new RoundedPanel(10, UITheme.ACCENT_LIGHT, null);
        chip.setLayout(new MigLayout("insets 3 10 3 10", "[]", "[]"));
        JLabel label = new JLabel(text);
        label.setFont(UITheme.fontChip());
        label.setForeground(UITheme.ACCENT_DARK);
        chip.add(label);
        return chip;
    }

    private JComponent buildStatusBadge(Status status) {
        RoundedPanel badge = new RoundedPanel(10, status.background, null);
        badge.setLayout(new MigLayout("insets 4 10 4 10", "[]", "[]"));
        JLabel label = new JLabel(status.label.toUpperCase());
        label.setFont(UITheme.fontStatusBadge());
        label.setForeground(status.foreground);
        badge.add(label);
        return badge;
    }
}
