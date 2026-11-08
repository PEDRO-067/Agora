package View;

import Model.Artigo;
import java.util.List;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/** Lista os artigos retornados pela pesquisa. */
public class PainelConteudo extends ScrollPane {
    private final FlowPane grade = new FlowPane(18, 18);

    public PainelConteudo() {
        setFitToWidth(true);
        grade.setPadding(new Insets(22));
        grade.setBackground(TemaUI.getFundo(TemaUI.COR_FUNDO_APP));
        setContent(grade);
    }

    public void atualizar(List<Artigo> artigos, Consumer<Artigo> abrir) {
        grade.getChildren().clear();
        if (artigos.isEmpty()) {
            grade.getChildren().add(new Label("Nenhum artigo encontrado. Cadastre um DOI ou ajuste os filtros."));
        }
        for (Artigo artigo : artigos) {
            grade.getChildren().add(new CartaoArtigo(artigo, () -> abrir.accept(artigo)));
        }
    }
}
