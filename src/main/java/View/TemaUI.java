package View;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class TemaUI {
    public static final String COR_FUNDO_APP = "#F3F4F6";
    public static final String COR_FUNDO_PAINEL = "#FFFFFF";
    public static final String COR_PRIMARIA = "#3B82F6";
    public static final String COR_SECUNDARIA = "#1E40AF";
    public static final String COR_TEXTO = "#1F2937";
    public static final String COR_TEXTO_SECUNDARIO = "#6B7280";

    public static Background getFundo(String corHex) {
        return new Background(new BackgroundFill(Color.web(corHex), CornerRadii.EMPTY, Insets.EMPTY));
    }
}
