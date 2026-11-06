/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AnotacaoDAO;
import Model.Anotacao;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author pedro
 */
public class AnotacaoCtrl {
    
    private final AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
    
    public Anotacao cadastrarAnotacao(String nome, String texto, int idArtigo) throws SQLException {
        Optional<Anotacao> existente = anotacaoDAO.buscarPorArtigo(idArtigo);
        if (existente.isPresent()) {
            return existente.get();
        }
        
        Anotacao anotacao = new Anotacao(nome, texto, idArtigo);
        int id = anotacaoDAO.inserir(anotacao);
        anotacao.setId(id);
        
        return anotacao;
    }
    
    public boolean alterarAnotacao(int id, String novoTexto) throws SQLException {
        Optional<Anotacao> existente = anotacaoDAO.buscarPorId(id);
        if(!existente.isPresent()) {
            return false;
        }
        
        Anotacao anotacao = existente.get();
        anotacao.editarTexto(novoTexto);
        
        return anotacaoDAO.atualizar(anotacao);
    }
    
    public boolean excluirAnotacao(int id) throws SQLException {
        return anotacaoDAO.excluir(id);
    }
    
    public Optional<Anotacao> buscarPorId(int id) throws SQLException {
        return anotacaoDAO.buscarPorId(id);
    }
}