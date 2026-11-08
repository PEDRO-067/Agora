package View;

import Model.Pasta;
import Model.Tag;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/** Filtros de organização e acesso ao gerenciamento de pastas e tags. */
public class PainelMenuLateral extends VBox {
    private final ComboBox<Pasta> pastas = new ComboBox<>();
    private final ComboBox<Tag> tags = new ComboBox<>();

    public PainelMenuLateral(Runnable filtrar, Runnable gerenciarPastas, Runnable gerenciarTags) {
        setPadding(new Insets(22));
        setSpacing(14);
        setPrefWidth(250);
        setStyle("-fx-background-color: #E8EEF5;");
        pastas.setMaxWidth(Double.MAX_VALUE);
        tags.setMaxWidth(Double.MAX_VALUE);
        pastas.setConverter(new StringConverter<>() {
            public String toString(Pasta pasta) { return pasta == null ? "Todas as pastas" : pasta.getNome(); }
            public Pasta fromString(String texto) { throw new UnsupportedOperationException(); }
        });
        tags.setConverter(new StringConverter<>() {
            public String toString(Tag tag) { return tag == null ? "Todas as tags" : tag.getNome(); }
            public Tag fromString(String texto) { throw new UnsupportedOperationException(); }
        });
        pastas.setPromptText("Todas as pastas");
        tags.setPromptText("Todas as tags");
        Button aplicar = new Button("Aplicar filtros");
        aplicar.setOnAction(evento -> filtrar.run());
        Button limpar = new Button("Todas as pastas e tags");
        limpar.setOnAction(evento -> {
            pastas.setValue(null);
            tags.setValue(null);
            filtrar.run();
        });
        Button editarPastas = new Button("Gerenciar pastas");
        editarPastas.setOnAction(evento -> gerenciarPastas.run());
        Button editarTags = new Button("Gerenciar tags");
        editarTags.setOnAction(evento -> gerenciarTags.run());
        getChildren().addAll(new Label("ORGANIZAR"), new Label("Pasta"), pastas,
                new Label("Tag"), tags, aplicar, limpar, new Separator(), editarPastas, editarTags);
    }

    public int getIdPasta() { return pastas.getValue() == null ? 0 : pastas.getValue().getId(); }
    public int getIdTag() { return tags.getValue() == null ? 0 : tags.getValue().getId(); }

    public void atualizar(List<Pasta> novasPastas, List<Tag> novasTags) {
        int pastaId = getIdPasta();
        int tagId = getIdTag();
        pastas.getItems().setAll(novasPastas);
        tags.getItems().setAll(novasTags);
        pastas.setValue(null);
        tags.setValue(null);
        for (Pasta pasta : novasPastas) {
            if (pasta.getId() == pastaId) pastas.setValue(pasta);
        }
        for (Tag tag : novasTags) {
            if (tag.getId() == tagId) tags.setValue(tag);
        }
    }
}
