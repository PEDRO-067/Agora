package View;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/** Executa operações de banco/API em segundo plano e apresenta o resultado na tela. */
public final class OperacaoUI {
    private OperacaoUI() {}

    public static <T> void executar(Node painel, Callable<T> operacao, Consumer<T> sucesso) {
        painel.setDisable(true);
        painel.setCursor(Cursor.WAIT);
        Task<T> tarefa = new Task<>() {
            @Override
            protected T call() throws Exception {
                return operacao.call();
            }
        };
        tarefa.setOnSucceeded(evento -> {
            painel.setDisable(false);
            painel.setCursor(Cursor.DEFAULT);
            sucesso.accept(tarefa.getValue());
        });
        tarefa.setOnFailed(evento -> {
            painel.setDisable(false);
            painel.setCursor(Cursor.DEFAULT);
            erro(tarefa.getException());
        });
        Thread thread = new Thread(tarefa, "agora-operacao");
        thread.setDaemon(true);
        thread.start();
    }

    public static void erro(Throwable erro) {
        String mensagem = erro.getMessage();
        if (erro instanceof SQLException sql) {
            if ("23505".equals(sql.getSQLState())) {
                mensagem = "Esse cadastro já existe. Atualize a lista antes de tentar novamente.";
            } else if ("23503".equals(sql.getSQLState())) {
                mensagem = "Um registro relacionado foi removido. Atualize a tela.";
            } else if ("42P01".equals(sql.getSQLState())) {
                mensagem = "As tabelas do banco precisam ser preparadas com o arquivo schema.sql.";
            } else if (sql.getSQLState() != null && sql.getSQLState().startsWith("08")) {
                mensagem = "Não foi possível conectar ao PostgreSQL. Confira o serviço e config.properties.";
            }
        }
        Alert alerta = new Alert(Alert.AlertType.ERROR, mensagem == null ? erro.toString() : mensagem);
        alerta.setHeaderText("Não foi possível concluir a operação");
        alerta.showAndWait();
    }

    public static boolean confirmar(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO);
        alerta.setHeaderText("Confirmar exclusão");
        return alerta.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    public static void verificarAlteracao(boolean alterou) {
        if (!alterou) {
            throw new IllegalArgumentException("O registro não foi encontrado. Atualize a tela.");
        }
    }
}
