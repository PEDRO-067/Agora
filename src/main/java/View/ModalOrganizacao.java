package View;

import Controller.PastaCtrl;
import Controller.TagCtrl;
import Model.Pasta;
import Model.Tag;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/** Cadastro, renomeação e exclusão de pastas ou tags em uma janela simples. */
public class ModalOrganizacao {
    private final boolean pastas;
    private final Runnable atualizar;
    private final PastaCtrl pastaCtrl = new PastaCtrl();
    private final TagCtrl tagCtrl = new TagCtrl();
    private final ListView<String> lista = new ListView<>();
    private final List<Integer> ids = new ArrayList<>();
    private final TextField nome = new TextField();
    private final VBox painel = new VBox(12);

    public ModalOrganizacao(boolean pastas, Runnable atualizar) {
        this.pastas = pastas;
        this.atualizar = atualizar;
    }

    public void exibir(Window dono) {
        Stage janela = new Stage();
        janela.initOwner(dono);
        janela.initModality(Modality.WINDOW_MODAL);
        janela.setTitle(pastas ? "Gerenciar pastas" : "Gerenciar tags");
        nome.setPromptText("Nome");
        lista.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, atual) -> nome.setText(atual == null ? "" : atual));
        Button criar = new Button("Criar");
        criar.setOnAction(evento -> salvar(true));
        Button renomear = new Button("Renomear selecionada");
        renomear.disableProperty().bind(lista.getSelectionModel().selectedItemProperty().isNull());
        renomear.setOnAction(evento -> salvar(false));
        Button excluir = new Button("Excluir selecionada");
        excluir.disableProperty().bind(lista.getSelectionModel().selectedItemProperty().isNull());
        excluir.setOnAction(evento -> {
            int id = ids.get(lista.getSelectionModel().getSelectedIndex());
            if (OperacaoUI.confirmar("Excluir a organização selecionada e seus vínculos? Os artigos serão preservados.")) {
                OperacaoUI.executar(painel, () -> {
                    boolean resultado = pastas ? pastaCtrl.excluir(id) : tagCtrl.excluir(id);
                    OperacaoUI.verificarAlteracao(resultado);
                    return resultado;
                }, resultado -> carregar());
            }
        });
        painel.setPadding(new Insets(20));
        painel.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13;");
        janela.setOnCloseRequest(evento -> {
            if (painel.isDisabled()) evento.consume();
        });
        painel.getChildren().addAll(lista, nome, new HBox(8, criar, renomear), excluir);
        VBox.setVgrow(lista, Priority.ALWAYS);
        janela.setScene(new Scene(painel, 460, 470));
        janela.setOnHidden(evento -> atualizar.run());
        janela.show();
        carregar();
    }

    private void salvar(boolean criar) {
        String texto = nome.getText();
        int id = criar ? 0 : ids.get(lista.getSelectionModel().getSelectedIndex());
        OperacaoUI.executar(painel, () -> {
            if (pastas) {
                if (criar) pastaCtrl.cadastrar(texto);
                else OperacaoUI.verificarAlteracao(pastaCtrl.renomear(id, texto));
            } else {
                if (criar) tagCtrl.cadastrar(texto);
                else OperacaoUI.verificarAlteracao(tagCtrl.renomear(id, texto));
            }
            return true;
        }, resultado -> carregar());
    }

    private void carregar() {
        OperacaoUI.executar(painel, () -> {
            List<Item> itens = new ArrayList<>();
            if (pastas) {
                for (Pasta pasta : pastaCtrl.listar()) itens.add(new Item(pasta.getId(), pasta.getNome()));
            } else {
                for (Tag tag : tagCtrl.listar()) itens.add(new Item(tag.getId(), tag.getNome()));
            }
            return itens;
        }, itens -> {
            lista.getItems().clear();
            ids.clear();
            for (Item item : itens) {
                ids.add(item.id);
                lista.getItems().add(item.nome);
            }
            nome.clear();
        });
    }

    private static class Item {
        final int id;
        final String nome;
        Item(int id, String nome) { this.id = id; this.nome = nome; }
    }
}
