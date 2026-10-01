package com.papermanager.ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Ponto de entrada do prototipo visual. Nao ha nenhuma logica de
 * dominio/persistencia aqui - apenas sobe a janela principal.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Segue com o look and feel padrao caso o do sistema nao esteja disponivel.
            }
            new MainFrame().setVisible(true);
        });
    }
}
