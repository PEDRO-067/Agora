package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalCadastroArtigo {
    
    public void exibir() {
        Stage janela = new Stage();
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.setTitle("Cadastrar Artigo via DOI");
        janela.setMinWidth(400);
        
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        
        Label lblInstrucao = new Label("Insira o DOI do artigo:");
        lblInstrucao.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        TextField txtDoi = new TextField();
        txtDoi.setPromptText("Ex: 10.1109/TSE.2023.1234567");
        
        Button btnBuscar = new Button("Buscar e Cadastrar (Crossref)");
        btnBuscar.setStyle("-fx-background-color: " + TemaUI.COR_PRIMARIA + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnBuscar.setOnAction(e -> {
            System.out.println("Integração com Crossref pendente para o DOI: " + txtDoi.getText());
            janela.close();
        });
        
        layout.getChildren().addAll(lblInstrucao, txtDoi, btnBuscar);
        
        Scene cena = new Scene(layout);
        janela.setScene(cena);
        janela.showAndWait();
    }
}
