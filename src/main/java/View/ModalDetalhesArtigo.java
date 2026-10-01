package View;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalDetalhesArtigo {
    
    private String titulo;
    
    public ModalDetalhesArtigo(String titulo) {
        this.titulo = titulo;
    }
    
    public void exibir() {
        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.setTitle("Detalhes do Artigo");
        janela.setMinWidth(500);
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        
        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblTitulo.setWrapText(true);
        
        Label lblNota = new Label("Nota: Sem nota (Pendente integração)");
        Label lblTags = new Label("Tags: Nenhuma (Pendente integração)");
        
        TextArea txtAnotacoes = new TextArea();
        txtAnotacoes.setPromptText("Escreva suas anotações aqui...");
        txtAnotacoes.setPrefRowCount(4);
        
        Button btnGerarRef = new Button("Gerar Referência ABNT/BibTeX");
        btnGerarRef.setStyle("-fx-background-color: " + TemaUI.COR_SECUNDARIA + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnGerarRef.setOnAction(e -> {
            System.out.println("Geração de referência pendente!");
        });
        
        Button btnSalvar = new Button("Salvar Anotações");
        btnSalvar.setOnAction(e -> janela.close());
        
        layout.getChildren().addAll(lblTitulo, lblNota, lblTags, new Label("Anotações:"), txtAnotacoes, btnGerarRef, btnSalvar);
        
        Scene cena = new Scene(layout);
        janela.setScene(cena);
        janela.showAndWait();
    }
}
