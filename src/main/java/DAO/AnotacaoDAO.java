/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Anotacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 *
 * @author gyudi
 */
public class AnotacaoDAO {
    
    public int inserir(Anotacao anotacao) throws SQLException {
        String sql = "INSERT INTO anotacao (nome, id_artigo, texto, dataCriacao, dataEdicao) VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try(Connection conexao = ConexaoBD.getConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
           
            comando.setString(1, anotacao.getNome());
            comando.setInt(2, anotacao.getArtigoId());
            comando.setString(3, anotacao.getTexto());
            comando.setObject(4, anotacao.getDataCriacao());
            comando.setObject(5, anotacao.getDataEdicao());
           
            try (ResultSet resultado = comando.executeQuery()) {
                if(resultado.next()) {
                    return resultado.getInt("id");
                }
                throw new SQLException("O banco não retornou o ID da anotação");
            }
        } 
    }
    
    public Optional<Anotacao> buscarPorArtigo(int idArtigo) throws SQLException {
        String sql = "SELECT * FROM anotacao WHERE id_artigo = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setInt(1, idArtigo);
            
            try(ResultSet resultado = comando.executeQuery()) {
                if(resultado.next()) {
                    return Optional.of(lerAnotacao(resultado));
                }
                return Optional.empty();
            }
        }
    }
    
    public Optional<Anotacao> buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM anotacao WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerAnotacao(resultado));
                }
                return Optional.empty();
            }
        }
    }
    
    public boolean atualizar(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacao SET texto = ?, dataEdicao = ? WHERE id = ?";
        
        try(Connection conexao = ConexaoBD.getConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setString(1, anotacao.getTexto());
            comando.setObject(2, anotacao.getDataEdicao());
            comando.setInt(3, anotacao.getId());
            return comando.executeUpdate() == 1;
        }
    }
    
    private Anotacao lerAnotacao(ResultSet resultado) throws SQLException {
        LocalDate dataEdicao;
        
        if(resultado.getDate("dataCriacao") == null) {
            dataEdicao = null;
        } else {
            dataEdicao = resultado.getDate("dataCriacao").toLocalDate();
        }
        
        return new Anotacao(resultado.getInt("id"), resultado.getString("nome"), resultado.getString("texto"), resultado.getDate("dataCriacao").toLocalDate(), dataEdicao, resultado.getInt("artigo_id"));
    } 
    
    public boolean excluir(int idAnotacao) throws SQLException {
        String sql = "DELETE FROM anotacao WHERE id = ?";
        
        try(Connection conexao = ConexaoBD.getConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {
                
            comando.setInt(1, idAnotacao);
            return comando.executeUpdate() == 1;
        }
    }
    
}
