/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AnotacaoDAO;
import DAO.ArtigoDAO;
import Model.Anotacao;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author pedro
 */
public class AnotacaoCtrl {
    
    private final AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
    
    public Anotacao cadastrarAnotacao(String nome, String texto, int idArtigo) throws SQLException {
        Anotacao anotacao = new Anotacao(nome, texto, idArtigo);
        if (new ArtigoDAO().buscarPorId(idArtigo).isEmpty()) {
            throw new IllegalArgumentException("Artigo não encontrado.");
        }
        Optional<Anotacao> existente = anotacaoDAO.buscarPorArtigo(idArtigo);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Este artigo já possui uma anotação. Use a edição.");
        }
        
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

    /** Consulta a anotação do artigo selecionado. */
    public Optional<Anotacao> buscarPorArtigo(int idArtigo) throws SQLException {
        return anotacaoDAO.buscarPorArtigo(idArtigo);
    }

    /** Atualiza nome e texto mantendo o vínculo e a data de criação. */
    public boolean alterarAnotacao(int id, String nome, String texto) throws SQLException {
        Optional<Anotacao> existente = anotacaoDAO.buscarPorId(id);
        if (existente.isEmpty()) {
            return false;
        }
        Anotacao anotacao = existente.get();
        anotacao.setNome(nome);
        anotacao.editarTexto(texto);
        return anotacaoDAO.atualizar(anotacao);
    }
}
