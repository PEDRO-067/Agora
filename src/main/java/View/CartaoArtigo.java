package View;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Cursor;

public class CartaoArtigo extends VBox {
    
    // Construtor recebe os dados primitivos temporariamente, 
    // mas na integração real deve receber o objeto Model.Artigo
    public CartaoArtigo(String tituloArtigo, String autores, int ano, String doi) {
        this.setBackground(TemaUI.getFundo(TemaUI.COR_FUNDO_PAINEL));
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.setPrefWidth(300);
        this.setPrefHeight(200);
        this.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #E5E7EB; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");
        this.setCursor(Cursor.HAND);
        
        Label titulo = new Label(tituloArtigo);
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: " + TemaUI.COR_TEXTO + ";");
        titulo.setWrapText(true);
        
        Label aut = new Label(autores + " (" + ano + ")");
        aut.setFont(Font.font("Segoe UI", 14));
        aut.setStyle("-fx-text-fill: " + TemaUI.COR_TEXTO_SECUNDARIO + ";");
        
        Label ldoi = new Label("DOI: " + doi);
        ldoi.setFont(Font.font("Segoe UI", 12));
        ldoi.setStyle("-fx-text-fill: #9CA3AF;");
        
        this.getChildren().addAll(titulo, aut, ldoi);
        
        this.setOnMouseClicked(e -> {
            ModalDetalhesArtigo modal = new ModalDetalhesArtigo(tituloArtigo);
            modal.exibir();
        });
    }
}
