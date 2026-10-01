package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class PainelCabecalho extends HBox {
    
    public PainelCabecalho() {
        this.setBackground(TemaUI.getFundo(TemaUI.COR_PRIMARIA));
        this.setPadding(new Insets(15, 25, 15, 25));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);
        
        Label titulo = new Label("ÁGORA");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);
        
        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);
        
        Button btnCadastrar = new Button("+ Novo Artigo (DOI)");
        btnCadastrar.setStyle("-fx-background-color: #ffffff; -fx-text-fill: " + TemaUI.COR_PRIMARIA + "; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCadastrar.setOnAction(e -> {
            ModalCadastroArtigo modal = new ModalCadastroArtigo();
            modal.exibir();
        });
        
        this.getChildren().addAll(titulo, espacador, btnCadastrar);
    }
}
