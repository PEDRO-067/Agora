package View;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import Model.Artigo;

public class PainelConteudo extends ScrollPane {
    
    public PainelConteudo() {
        this.setFitToWidth(true);
        this.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        
        FlowPane painelGrid = new FlowPane();
        painelGrid.setPadding(new Insets(30));
        painelGrid.setHgap(20);
        painelGrid.setVgap(20);
        painelGrid.setBackground(TemaUI.getFundo(TemaUI.COR_FUNDO_APP));
        
        // Dados Falsos (Mock) para demonstração.
        // Futuramente, a lista será carregada do ArtigoController que se comunica com ArtigoDAO
        for (int i = 1; i <= 6; i++) {
            CartaoArtigo cartao = new CartaoArtigo("Artigo de Inteligência Artificial " + i, "Autores Fictícios " + i, 2024, "10.1109/MOCK." + i);
            painelGrid.getChildren().add(cartao);
        }
        
        this.setContent(painelGrid);
    }
}
