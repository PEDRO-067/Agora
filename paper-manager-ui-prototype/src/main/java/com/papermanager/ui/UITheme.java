package com.papermanager.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Constantes visuais centralizadas: cores, fontes e medidas.
 * Mantidas em um unico lugar para que a interface tenha aparencia
 * consistente e para facilitar ajustes futuros (ex: tema escuro).
 */
public final class UITheme {

    private UITheme() {
    }

    // Fundo
    public static final Color BG_APP = Color.WHITE;
    public static final Color BG_SIDEBAR = new Color(0xF7, 0xF7, 0xF5);
    public static final Color BG_HEADER = Color.WHITE;
    public static final Color BG_CARD = Color.WHITE;
    public static final Color BG_SEARCH = new Color(0xF0, 0xF0, 0xEE);

    // Bordas / linhas
    public static final Color BORDER = new Color(0xE4, 0xE4, 0xE0);
    public static final Color BORDER_CARD = new Color(0xE7, 0xE7, 0xE3);

    // Texto
    public static final Color TEXT_PRIMARY = new Color(0x1F, 0x23, 0x28);
    public static final Color TEXT_SECONDARY = new Color(0x6B, 0x72, 0x7C);
    public static final Color TEXT_MUTED = new Color(0x9C, 0xA3, 0xAF);

    // Acento (usado em selecao, links e acao primaria)
    public static final Color ACCENT = new Color(0x3F, 0x5B, 0x9E);
    public static final Color ACCENT_LIGHT = new Color(0xE9, 0xED, 0xF7);
    public static final Color ACCENT_DARK = new Color(0x2C, 0x40, 0x71);

    // Estados de leitura
    public static final Color STATUS_LIDO_BG = new Color(0xE3, 0xF3, 0xE7);
    public static final Color STATUS_LIDO_FG = new Color(0x22, 0x7A, 0x3E);
    public static final Color STATUS_LENDO_BG = new Color(0xFB, 0xF0, 0xDD);
    public static final Color STATUS_LENDO_FG = new Color(0xA1, 0x6A, 0x0C);
    public static final Color STATUS_NAO_LIDO_BG = new Color(0xEE, 0xEE, 0xEC);
    public static final Color STATUS_NAO_LIDO_FG = new Color(0x6B, 0x72, 0x7C);

    public static final Color HOVER = new Color(0xEC, 0xEC, 0xE9);

    // Fontes
    private static final String FAMILY = "SansSerif";

    public static Font fontLogo() {
        return new Font(FAMILY, Font.BOLD, 18);
    }

    public static Font fontSectionLabel() {
        return new Font(FAMILY, Font.BOLD, 11);
    }

    public static Font fontNavItem() {
        return new Font(FAMILY, Font.PLAIN, 14);
    }

    public static Font fontNavItemSelected() {
        return new Font(FAMILY, Font.BOLD, 14);
    }

    public static Font fontPageTitle() {
        return new Font(FAMILY, Font.BOLD, 22);
    }

    public static Font fontPageSubtitle() {
        return new Font(FAMILY, Font.PLAIN, 13);
    }

    public static Font fontCardTitle() {
        return new Font(FAMILY, Font.BOLD, 15);
    }

    public static Font fontCardMeta() {
        return new Font(FAMILY, Font.PLAIN, 13);
    }

    public static Font fontChip() {
        return new Font(FAMILY, Font.PLAIN, 12);
    }

    public static Font fontStatusBadge() {
        return new Font(FAMILY, Font.BOLD, 11);
    }

    public static Font fontButton() {
        return new Font(FAMILY, Font.BOLD, 13);
    }
}
