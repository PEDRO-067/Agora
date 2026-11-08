package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/** Cabeçalho com o cadastro de referências. */
public class PainelCabecalho extends HBox {
    public PainelCabecalho(Runnable cadastrar) {
        setPadding(new Insets(20, 25, 20, 25));
        setSpacing(20);
        setStyle("-fx-background-color: #1E3A5F; -fx-alignment: center-left;");
        Label titulo = new Label("ÁGORA");
        titulo.setStyle("-fx-font-size: 25; -fx-font-weight: bold; -fx-text-fill: white;");
        Label descricao = new Label("Sua biblioteca de artigos");
        descricao.setStyle("-fx-text-fill: #DCE7F5;");
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        Button novo = new Button("+ Cadastrar por DOI");
        novo.setOnAction(evento -> cadastrar.run());
        getChildren().addAll(titulo, descricao, espaco, novo);
    }
}
