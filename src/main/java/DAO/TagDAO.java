package DAO;

import Model.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Persiste tags e seus vínculos com artigos. */
public class TagDAO {
    public int inserir(Tag item) throws SQLException {
        String sql = "INSERT INTO tag (nome) VALUES (?) RETURNING id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, item.getNome());
            try (ResultSet resultado = comando.executeQuery()) {
                resultado.next();
                return resultado.getInt("id");
            }
        }
    }

    public Optional<Tag> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome FROM tag WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(new Tag(resultado.getInt("id"), resultado.getString("nome")));
                }
                return Optional.empty();
            }
        }
    }

    public List<Tag> listar() throws SQLException {
        List<Tag> itens = new ArrayList<>();
        String sql = "SELECT id, nome FROM tag ORDER BY lower(nome), id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                itens.add(new Tag(resultado.getInt("id"), resultado.getString("nome")));
            }
        }
        return itens;
    }

    public List<Tag> listarPorArtigo(int idArtigo) throws SQLException {
        List<Tag> itens = new ArrayList<>();
        String sql = "SELECT t.id, t.nome FROM tag t JOIN artigo_tag v ON v.id_tag = t.id "
                + "WHERE v.id_artigo = ? ORDER BY lower(t.nome), t.id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idArtigo);
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    itens.add(new Tag(resultado.getInt("id"), resultado.getString("nome")));
                }
            }
        }
        return itens;
    }

    public boolean atualizar(Tag item) throws SQLException {
        String sql = "UPDATE tag SET nome = ? WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, item.getNome());
            comando.setInt(2, item.getId());
            return comando.executeUpdate() == 1;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM tag WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            return comando.executeUpdate() == 1;
        }
    }

    /** Retorna false quando o vínculo já existia. */
    public boolean associarArtigo(int id, int idArtigo) throws SQLException {
        String sql = "INSERT INTO artigo_tag (id_tag, id_artigo) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.setInt(2, idArtigo);
            return comando.executeUpdate() == 1;
        }
    }

    public boolean removerArtigo(int id, int idArtigo) throws SQLException {
        String sql = "DELETE FROM artigo_tag WHERE id_tag = ? AND id_artigo = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.setInt(2, idArtigo);
            return comando.executeUpdate() == 1;
        }
    }
}
