package View;

import Controller.*;
import Model.*;
import java.net.URI;
import java.util.List;
import java.awt.Desktop;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.stage.*;

/** Detalhes bibliográficos, leitura, anotação e organização de um artigo. */
public class ModalDetalhesArtigo {
    private final int idArtigo;
    private final Runnable atualizar;
    private final ArtigoCtrl artigoCtrl = new ArtigoCtrl();
    private final AnotacaoCtrl anotacaoCtrl = new AnotacaoCtrl();
    private final PastaCtrl pastaCtrl = new PastaCtrl();
    private final TagCtrl tagCtrl = new TagCtrl();
    private final VBox painel = new VBox(15);
    private final Stage janela = new Stage();

    public ModalDetalhesArtigo(int idArtigo, Runnable atualizar) {
        this.idArtigo = idArtigo;
        this.atualizar = atualizar;
    }

    public void exibir(Window dono) {
        janela.initOwner(dono);
        janela.initModality(Modality.WINDOW_MODAL);
        janela.setTitle("Detalhes do artigo");
        painel.setPadding(new Insets(22));
        painel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13;");
        janela.setOnCloseRequest(evento -> {
            if (painel.isDisabled()) evento.consume();
        });
        ScrollPane scroll = new ScrollPane(painel);
        scroll.setFitToWidth(true);
        janela.setScene(new Scene(scroll, 800, 700));
        janela.setOnHidden(evento -> atualizar.run());
        janela.show();
        carregar();
    }

    private void carregar() {
        OperacaoUI.executar(painel, () -> {
            Artigo artigo = artigoCtrl.buscarDetalhes(idArtigo);
            return new Detalhes(artigo, pastaCtrl.listar(), pastaCtrl.listarPorArtigo(idArtigo), tagCtrl.listar());
        }, this::mostrar);
    }

    private void mostrar(Detalhes dados) {
        Artigo artigo = dados.artigo;
        painel.getChildren().clear();
        Label titulo = new Label(valor(artigo.getTitulo()));
        titulo.setWrapText(true);
        titulo.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        painel.getChildren().add(titulo);

        TextArea metadados = new TextArea(
                "DOI: " + artigo.getDoi()
                + "\nAutores: " + String.join("; ", artigo.getAutores())
                + "\nAno: " + (artigo.getAno() == 0 ? "Não informado" : artigo.getAno())
                + "\nMês: " + (artigo.getMes() == 0 ? "Não informado" : artigo.getMes())
                + "\nTipo de publicação: " + valor(artigo.getTipoPublicacao())
                + "\nVeículo: " + valor(artigo.getVeiculo())
                + "\nVolume: " + valor(artigo.getVolume())
                + "\nNúmero: " + valor(artigo.getNumero())
                + "\nNúmero do artigo: " + valor(artigo.getNumeroArtigo())
                + "\nEditora: " + valor(artigo.getEditora())
                + "\nOrganização: " + valor(artigo.getOrganizacao())
                + "\nLocal: " + valor(artigo.getEndereco())
                + "\nPáginas: " + valor(artigo.getPaginas())
                + "\nURL: " + valor(artigo.getUrl())
                + "\n\nResumo\n" + valor(artigo.getResumo()));
        metadados.setEditable(false);
        metadados.setWrapText(true);
        metadados.setPrefRowCount(9);

        ComboBox<String> status = new ComboBox<>();
        status.getItems().addAll(Artigo.NAO_LIDO, Artigo.EM_LEITURA, Artigo.LIDO);
        status.setValue(artigo.getStatusLeitura());
        Button salvarStatus = new Button("Salvar status");
        salvarStatus.setOnAction(evento -> {
            String escolhido = status.getValue();
            OperacaoUI.executar(painel, () -> {
                OperacaoUI.verificarAlteracao(artigoCtrl.alterarStatus(idArtigo, escolhido));
                return true;
            }, resultado -> carregar());
        });
        Button abrir = new Button("Abrir na web");
        abrir.setDisable(artigo.getUrl() == null || artigo.getUrl().isBlank());
        abrir.setOnAction(evento -> abrirUrl(artigo.getUrl()));
        Button bibtex = new Button("Gerar BibTeX");
        bibtex.setOnAction(evento ->
                OperacaoUI.executar(painel, () -> artigoCtrl.gerarBibtex(idArtigo), this::mostrarBibtex));
        painel.getChildren().addAll(metadados, new HBox(10, status, salvarStatus, abrir, bibtex), new Separator());

        mostrarAnotacao(artigo.getAnotacao());
        mostrarPastas(dados);
        mostrarTags(dados);
        Button excluir = new Button("Excluir artigo da biblioteca");
        excluir.setStyle("-fx-text-fill: #A4262C;");
        excluir.setOnAction(evento -> {
            if (OperacaoUI.confirmar("Excluir este artigo, sua anotação e seus vínculos com pastas e tags?")) {
                OperacaoUI.executar(painel, () -> {
                    OperacaoUI.verificarAlteracao(artigoCtrl.excluir(idArtigo));
                    return true;
                }, resultado -> janela.close());
            }
        });
        painel.getChildren().addAll(new Separator(), excluir);
    }

    private void mostrarAnotacao(Anotacao anotacao) {
        TextField nome = new TextField(anotacao == null ? "" : anotacao.getNome());
        nome.setPromptText("Título da anotação");
        TextArea texto = new TextArea(anotacao == null ? "" : anotacao.getTexto());
        texto.setPromptText("Escreva sua anotação");
        texto.setWrapText(true);
        texto.setPrefRowCount(4);
        Button salvar = new Button(anotacao == null ? "Criar anotação" : "Salvar anotação");
        salvar.setOnAction(evento -> {
            String novoNome = nome.getText();
            String novoTexto = texto.getText();
            OperacaoUI.executar(painel, () -> {
                if (anotacao == null) {
                    anotacaoCtrl.cadastrarAnotacao(novoNome, novoTexto, idArtigo);
                } else {
                    OperacaoUI.verificarAlteracao(
                            anotacaoCtrl.alterarAnotacao(anotacao.getId(), novoNome, novoTexto));
                }
                return true;
            }, resultado -> carregar());
        });
        Button excluir = new Button("Excluir anotação");
        excluir.setDisable(anotacao == null);
        excluir.setOnAction(evento -> {
            if (OperacaoUI.confirmar("Excluir a anotação deste artigo?")) {
                OperacaoUI.executar(painel, () -> {
                    OperacaoUI.verificarAlteracao(anotacaoCtrl.excluirAnotacao(anotacao.getId()));
                    return true;
                }, resultado -> carregar());
            }
        });
        String datas = anotacao == null ? "Ainda não há anotação." : "Criada em " + anotacao.getDataCriacao()
                + (anotacao.getDataEdicao() == null ? "" : " • Editada em " + anotacao.getDataEdicao());
        painel.getChildren().addAll(new Label("ANOTAÇÃO"), new Label(datas), nome, texto,
                new HBox(10, salvar, excluir), new Separator());
    }

    private void mostrarPastas(Detalhes dados) {
        painel.getChildren().add(new Label("PASTAS"));
        if (dados.pastas.isEmpty()) painel.getChildren().add(new Label("Crie pastas em Gerenciar pastas na biblioteca."));
        for (Pasta pasta : dados.pastas) {
            boolean associado = false;
            for (Pasta vinculada : dados.vinculadas) {
                if (vinculada.getId() == pasta.getId()) associado = true;
            }
            Button acao = new Button(associado ? "Retirar" : "Adicionar");
            boolean remover = associado;
            acao.setOnAction(evento -> OperacaoUI.executar(painel, () -> {
                if (remover) return pastaCtrl.removerArtigo(pasta.getId(), idArtigo);
                return pastaCtrl.associarArtigo(pasta.getId(), idArtigo);
            }, resultado -> carregar()));
            painel.getChildren().add(new HBox(12, new Label(pasta.getNome()), acao));
        }
        painel.getChildren().add(new Separator());
    }

    private void mostrarTags(Detalhes dados) {
        painel.getChildren().add(new Label("TAGS"));
        if (dados.tags.isEmpty()) painel.getChildren().add(new Label("Crie tags em Gerenciar tags na biblioteca."));
        for (Tag tag : dados.tags) {
            boolean associado = false;
            for (Tag vinculada : dados.artigo.getTags()) {
                if (vinculada.getId() == tag.getId()) associado = true;
            }
            Button acao = new Button(associado ? "Retirar" : "Adicionar");
            boolean remover = associado;
            acao.setOnAction(evento -> OperacaoUI.executar(painel, () -> {
                if (remover) return tagCtrl.removerArtigo(tag.getId(), idArtigo);
                return tagCtrl.associarArtigo(tag.getId(), idArtigo);
            }, resultado -> carregar()));
            painel.getChildren().add(new HBox(12, new Label(tag.getNome()), acao));
        }
    }

    private void abrirUrl(String url) {
        try {
            URI uri = URI.create(url);
            if (!"https".equalsIgnoreCase(uri.getScheme()) && !"http".equalsIgnoreCase(uri.getScheme())) {
                throw new IllegalArgumentException("O endereço do artigo deve usar HTTP ou HTTPS.");
            }
            if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                throw new IllegalStateException("O sistema não oferece abertura automática do navegador. Copie a URL dos metadados.");
            }
            Desktop.getDesktop().browse(uri);
        } catch (Exception erro) {
            OperacaoUI.erro(erro);
        }
    }

    private void mostrarBibtex(String referencia) {
        TextArea texto = new TextArea(referencia);
        texto.setEditable(false);
        texto.setWrapText(true);
        Button copiar = new Button("Copiar");
        copiar.setOnAction(evento -> {
            ClipboardContent conteudo = new ClipboardContent();
            conteudo.putString(referencia);
            Clipboard.getSystemClipboard().setContent(conteudo);
            copiar.setText("Copiado");
        });
        VBox conteudo = new VBox(12, texto, copiar);
        conteudo.setPadding(new Insets(18));
        VBox.setVgrow(texto, Priority.ALWAYS);
        Stage referenciaJanela = new Stage();
        referenciaJanela.initOwner(janela);
        referenciaJanela.initModality(Modality.WINDOW_MODAL);
        referenciaJanela.setTitle("Referência BibTeX");
        referenciaJanela.setScene(new Scene(conteudo, 660, 400));
        referenciaJanela.show();
    }

    private String valor(String texto) { return texto == null || texto.isBlank() ? "Não informado" : texto; }

    private static class Detalhes {
        final Artigo artigo;
        final List<Pasta> pastas;
        final List<Pasta> vinculadas;
        final List<Tag> tags;
        Detalhes(Artigo artigo, List<Pasta> pastas, List<Pasta> vinculadas, List<Tag> tags) {
            this.artigo = artigo;
            this.pastas = pastas;
            this.vinculadas = vinculadas;
            this.tags = tags;
        }
    }
}
