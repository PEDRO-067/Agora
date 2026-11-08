package View;

import Controller.ArtigoCtrl;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/** Importa os metadados e persiste o artigo pelo DOI informado. */
public class ModalCadastroArtigo {
    private final Runnable atualizar;

    public ModalCadastroArtigo(Runnable atualizar) { this.atualizar = atualizar; }

    public void exibir(Window dono) {
        Stage janela = new Stage();
        janela.initOwner(dono);
        janela.initModality(Modality.WINDOW_MODAL);
        janela.setTitle("Cadastrar artigo");
        TextField doi = new TextField();
        doi.setPromptText("DOI ou link https://doi.org/…");
        Label mensagem = new Label("Informe o DOI para consultar os metadados no Crossref.");
        mensagem.setWrapText(true);
        Button cadastrar = new Button("Buscar e cadastrar");
        VBox painel = new VBox(15, mensagem, doi, cadastrar);
        painel.setPadding(new Insets(24));
        painel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13;");
        janela.setOnCloseRequest(evento -> {
            if (painel.isDisabled()) evento.consume();
        });
        cadastrar.setOnAction(evento -> {
            String entrada = doi.getText();
            OperacaoUI.executar(painel, () -> new ArtigoCtrl().cadastrarPorDoi(entrada), artigo -> {
                janela.close();
                atualizar.run();
                new ModalDetalhesArtigo(artigo.getId(), atualizar).exibir(dono);
            });
        });
        doi.setOnAction(evento -> cadastrar.fire());
        janela.setScene(new Scene(painel, 470, 185));
        janela.show();
    }
}
