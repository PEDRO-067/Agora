/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Artigo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ArtigoDAO {

    public int inserir(Artigo artigo) throws SQLException {
        String sql = """
            INSERT INTO artigo (
                doi, titulo, autores, ano, tipo_publicacao, veiculo,
                volume, numero, numero_artigo, editora, organizacao,
                endereco, paginas, mes, resumo, url, status_leitura
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
            """;

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            String[] autores = artigo.getAutores().toArray(new String[0]);

            comando.setString(1, artigo.getDoi());
            comando.setString(2, artigo.getTitulo());
            comando.setArray(3, conexao.createArrayOf("text", autores));
            comando.setInt(4, artigo.getAno());
            comando.setString(5, artigo.getTipoPublicacao());
            comando.setString(6, artigo.getVeiculo());
            comando.setString(7, artigo.getVolume());
            comando.setString(8, artigo.getNumero());
            comando.setString(9, artigo.getNumeroArtigo());
            comando.setString(10, artigo.getEditora());
            comando.setString(11, artigo.getOrganizacao());
            comando.setString(12, artigo.getEndereco());
            comando.setString(13, artigo.getPaginas());
            comando.setInt(14, artigo.getMes());
            comando.setString(15, artigo.getResumo());
            comando.setString(16, artigo.getUrl());
            comando.setString(17, artigo.getStatusLeitura());

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("id");
                }
                throw new SQLException("O banco não retornou o ID do artigo.");
            }
        }
    }

    public Optional<Artigo> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM artigo WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerArtigo(resultado));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<Artigo> buscarPorDoi(String doi) throws SQLException {
        String sql = "SELECT * FROM artigo WHERE doi = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, doi);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerArtigo(resultado));
                }
                return Optional.empty();
            }
        }
    }

    public List<Artigo> listar() throws SQLException {
        String sql = "SELECT * FROM artigo ORDER BY titulo, id";
        List<Artigo> artigos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                artigos.add(lerArtigo(resultado));
            }
        }
        return artigos;
    }

    public boolean atualizarStatus(int id, String status) throws SQLException {
        String sql = "UPDATE artigo SET status_leitura = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, status);
            comando.setInt(2, id);
            return comando.executeUpdate() == 1;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM artigo WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);
            return comando.executeUpdate() == 1;
        }
    }

    private Artigo lerArtigo(ResultSet resultado) throws SQLException {
        Artigo artigo = new Artigo(resultado.getString("doi"));

        artigo.setId(resultado.getInt("id"));
        artigo.setTitulo(resultado.getString("titulo"));

        String[] autores = (String[]) resultado.getArray("autores").getArray();
        artigo.setAutores(Arrays.asList(autores));

        artigo.setAno(resultado.getInt("ano"));
        artigo.setTipoPublicacao(resultado.getString("tipo_publicacao"));
        artigo.setVeiculo(resultado.getString("veiculo"));
        artigo.setVolume(resultado.getString("volume"));
        artigo.setNumero(resultado.getString("numero"));
        artigo.setNumeroArtigo(resultado.getString("numero_artigo"));
        artigo.setEditora(resultado.getString("editora"));
        artigo.setOrganizacao(resultado.getString("organizacao"));
        artigo.setEndereco(resultado.getString("endereco"));
        artigo.setPaginas(resultado.getString("paginas"));
        artigo.setMes(resultado.getInt("mes"));
        artigo.setResumo(resultado.getString("resumo"));
        artigo.setUrl(resultado.getString("url"));
        artigo.setStatusLeitura(resultado.getString("status_leitura"));

        return artigo;
    }
}
