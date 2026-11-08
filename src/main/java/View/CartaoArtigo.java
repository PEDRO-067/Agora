package View;

import Model.Artigo;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/** Apresenta um registro real da biblioteca. */
public class CartaoArtigo extends VBox {
    public CartaoArtigo(Artigo artigo, Runnable abrir) {
        setPadding(new Insets(18));
        setSpacing(12);
        setPrefWidth(300);
        setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #DCE3EC; -fx-border-radius: 8;");
        Label titulo = new Label(artigo.getTitulo() == null ? "Título não informado" : artigo.getTitulo());
        titulo.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        titulo.setWrapText(true);
        Label autores = new Label(String.join("; ", artigo.getAutores()));
        autores.setWrapText(true);
        Label ano = new Label(artigo.getAno() == 0 ? "Ano não informado" : String.valueOf(artigo.getAno()));
        Label doi = new Label(artigo.getDoi());
        doi.setWrapText(true);
        Label status = new Label(artigo.getStatusLeitura());
        status.setStyle("-fx-text-fill: #245D8C;");
        Button detalhes = new Button("Abrir artigo");
        detalhes.setOnAction(evento -> abrir.run());
        getChildren().addAll(titulo, autores, ano, doi, status, detalhes);
    }
}
