package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Model.Pasta;

public class PainelMenuLateral extends VBox {
    
    public PainelMenuLateral() {
        this.setBackground(TemaUI.getFundo(TemaUI.COR_FUNDO_PAINEL));
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setPrefWidth(250);
        this.setStyle("-fx-border-color: #E5E7EB; -fx-border-width: 0 1 0 0;");
        
        Label lblNavegacao = new Label("NAVEGAÇÃO");
        lblNavegacao.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblNavegacao.setStyle("-fx-text-fill: " + TemaUI.COR_TEXTO_SECUNDARIO + ";");
        
        Label btnTodos = new Label("Todos os Artigos");
        btnTodos.setFont(Font.font("Segoe UI", 14));
        btnTodos.setStyle("-fx-text-fill: " + TemaUI.COR_TEXTO + "; -fx-cursor: hand;");
        
        Label lblPastas = new Label("PASTAS");
        lblPastas.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        lblPastas.setStyle("-fx-text-fill: " + TemaUI.COR_TEXTO_SECUNDARIO + ";");
        VBox.setMargin(lblPastas, new Insets(20, 0, 0, 0));
        
        ListView<String> listaPastas = new ListView<>();
        // Mock de pastas, futuramente integrado com Model.Pasta e PastaController
        listaPastas.getItems().addAll("Conferência IEEE", "TCC", "Referências POO");
        listaPastas.setStyle("-fx-background-insets: 0; -fx-padding: 0; -fx-background-color: transparent;");
        
        this.getChildren().addAll(lblNavegacao, btnTodos, lblPastas, listaPastas);
    }
}
