package View;

import Controller.ArtigoCtrl;
import Controller.PastaCtrl;
import Controller.TagCtrl;
import Model.Pasta;
import Model.Tag;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/** Integra a pesquisa e a navegação aos Controllers. */
public class TelaPrincipal extends BorderPane {
    private final ArtigoCtrl artigos = new ArtigoCtrl();
    private final PastaCtrl pastas = new PastaCtrl();
    private final TagCtrl tags = new TagCtrl();
    private final TextField titulo = new TextField();
    private final TextField ano = new TextField();
    private final Label quantidade = new Label();
    private final PainelConteudo conteudo = new PainelConteudo();
    private final PainelMenuLateral menu;

    public TelaPrincipal() {
        setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13;");
        menu = new PainelMenuLateral(this::pesquisar,
                () -> new ModalOrganizacao(true, this::atualizar).exibir(getScene().getWindow()),
                () -> new ModalOrganizacao(false, this::atualizar).exibir(getScene().getWindow()));
        titulo.setPromptText("Pesquisar pelo título");
        ano.setPromptText("Ano");
        ano.setPrefWidth(100);
        HBox.setHgrow(titulo, Priority.ALWAYS);
        Button buscar = new Button("Pesquisar");
        buscar.setOnAction(evento -> pesquisar());
        titulo.setOnAction(evento -> pesquisar());
        ano.setOnAction(evento -> pesquisar());
        Button atualizar = new Button("Atualizar");
        atualizar.setOnAction(evento -> atualizar());
        HBox pesquisa = new HBox(12, titulo, ano, buscar, atualizar);
        pesquisa.setPadding(new Insets(18));
        quantidade.setPadding(new Insets(0, 22, 12, 22));
        VBox centro = new VBox(pesquisa, quantidade, conteudo);
        VBox.setVgrow(conteudo, Priority.ALWAYS);
        setTop(new PainelCabecalho(() ->
                new ModalCadastroArtigo(this::atualizar).exibir(getScene().getWindow())));
        setLeft(menu);
        setCenter(centro);
        Platform.runLater(this::atualizar);
    }

    private void atualizar() {
        // As duas listas são carregadas juntas antes de atualizar os filtros.
        OperacaoUI.executar(this, () -> {
            List<Pasta> listaPastas = pastas.listar();
            List<Tag> listaTags = tags.listar();
            return new Organizacao(listaPastas, listaTags);
        }, organizacao -> {
            menu.atualizar(organizacao.pastas, organizacao.tags);
            pesquisar();
        });
    }

    private void pesquisar() {
        String texto = titulo.getText();
        String anoBusca = ano.getText();
        int pastaId = menu.getIdPasta();
        int tagId = menu.getIdTag();
        OperacaoUI.executar(this, () -> artigos.pesquisar(texto, anoBusca, pastaId, tagId), encontrados -> {
            quantidade.setText(encontrados.size() + " artigo(s)");
            conteudo.atualizar(encontrados, artigo ->
                    new ModalDetalhesArtigo(artigo.getId(), this::atualizar).exibir(getScene().getWindow()));
        });
    }

    private static class Organizacao {
        final List<Pasta> pastas;
        final List<Tag> tags;
        Organizacao(List<Pasta> pastas, List<Tag> tags) {
            this.pastas = pastas;
            this.tags = tags;
        }
    }
}
