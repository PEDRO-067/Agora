package DAO;

import Model.Pasta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Persiste pastas e seus vínculos com artigos. */
public class PastaDAO {
    public int inserir(Pasta item) throws SQLException {
        String sql = "INSERT INTO pasta (nome) VALUES (?) RETURNING id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, item.getNome());
            try (ResultSet resultado = comando.executeQuery()) {
                resultado.next();
                return resultado.getInt("id");
            }
        }
    }

    public Optional<Pasta> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome FROM pasta WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(new Pasta(resultado.getInt("id"), resultado.getString("nome")));
                }
                return Optional.empty();
            }
        }
    }

    public List<Pasta> listar() throws SQLException {
        List<Pasta> itens = new ArrayList<>();
        String sql = "SELECT id, nome FROM pasta ORDER BY lower(nome), id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                itens.add(new Pasta(resultado.getInt("id"), resultado.getString("nome")));
            }
        }
        return itens;
    }

    public List<Pasta> listarPorArtigo(int idArtigo) throws SQLException {
        List<Pasta> itens = new ArrayList<>();
        String sql = "SELECT t.id, t.nome FROM pasta t JOIN pasta_artigo v ON v.id_pasta = t.id "
                + "WHERE v.id_artigo = ? ORDER BY lower(t.nome), t.id";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, idArtigo);
            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    itens.add(new Pasta(resultado.getInt("id"), resultado.getString("nome")));
                }
            }
        }
        return itens;
    }

    public boolean atualizar(Pasta item) throws SQLException {
        String sql = "UPDATE pasta SET nome = ? WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, item.getNome());
            comando.setInt(2, item.getId());
            return comando.executeUpdate() == 1;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM pasta WHERE id = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            return comando.executeUpdate() == 1;
        }
    }

    /** Retorna false quando o vínculo já existia. */
    public boolean associarArtigo(int id, int idArtigo) throws SQLException {
        String sql = "INSERT INTO pasta_artigo (id_pasta, id_artigo) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.setInt(2, idArtigo);
            return comando.executeUpdate() == 1;
        }
    }

    public boolean removerArtigo(int id, int idArtigo) throws SQLException {
        String sql = "DELETE FROM pasta_artigo WHERE id_pasta = ? AND id_artigo = ?";
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.setInt(2, idArtigo);
            return comando.executeUpdate() == 1;
        }
    }
}
