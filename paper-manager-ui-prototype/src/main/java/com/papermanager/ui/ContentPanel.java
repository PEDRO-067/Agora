package com.papermanager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Area principal de conteudo. Nesta primeira versao representa sempre a
 * tela "Minha Biblioteca" com alguns papers ficticios, apenas para
 * validar o layout - sem busca, filtro ou navegacao real entre telas.
 */
public class ContentPanel extends JPanel {

    public ContentPanel() {
        setBackground(UITheme.BG_APP);
        setLayout(new MigLayout("insets 24 32 24 32, wrap, fillx", "[grow,fill]", "[]8[]20[grow]"));

        JLabel title = new JLabel("Minha Biblioteca");
        title.setFont(UITheme.fontPageTitle());
        title.setForeground(UITheme.TEXT_PRIMARY);
        add(title, "growx");

        JLabel subtitle = new JLabel("3 papers");
        subtitle.setFont(UITheme.fontPageSubtitle());
        subtitle.setForeground(UITheme.TEXT_SECONDARY);
        add(subtitle, "growx");

        add(buildPaperList(), "grow, push");
    }

    private JScrollPane buildPaperList() {
        JPanel list = new JPanel(new MigLayout("insets 0, wrap, fillx, gapy 14", "[grow,fill]", ""));
        list.setOpaque(false);

        list.add(new PaperCard(
                "Attention Is All You Need",
                "Vaswani et al.",
                2017,
                new String[]{"Transformers", "Deep Learning"},
                PaperCard.Status.LIDO));

        list.add(new PaperCard(
                "XGBoost: A Scalable Tree Boosting System",
                "Chen & Guestrin",
                2016,
                new String[]{"Machine Learning", "XGBoost"},
                PaperCard.Status.LENDO));

        list.add(new PaperCard(
                "BERT: Pre-training of Deep Bidirectional Transformers for Language Understanding",
                "Devlin et al.",
                2018,
                new String[]{"NLP", "Transformers"},
                PaperCard.Status.NAO_LIDO));

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
}
