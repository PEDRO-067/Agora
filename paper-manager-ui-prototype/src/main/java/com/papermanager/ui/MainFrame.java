package com.papermanager.ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * Janela principal da aplicacao. Monta a estrutura:
 * barra superior (span das duas colunas) + menu lateral fixo + area de
 * conteudo, usando MigLayout como gerenciador de layout do frame.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        super("PaperManager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 640));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel root = new JPanel(new MigLayout(
                "insets 0, gap 0, fill",
                "[220!]0[grow,fill]",
                "[64!]0[grow,fill]"));
        root.setBackground(UITheme.BG_APP);

        root.add(new HeaderPanel(), "cell 0 0 2 1, growx, height 64!");
        root.add(new SidebarPanel(), "cell 0 1, grow");
        root.add(new ContentPanel(), "cell 1 1, grow");

        setContentPane(root);
    }
}
