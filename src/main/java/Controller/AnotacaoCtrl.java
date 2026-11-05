/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AnotacaoDAO;
import Model.Anotacao;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author pedro
 */
public class AnotacaoCtrl {
    
    private AnotacaoDAO anotacaoDAO;

    public AnotacaoCtrl() {
        this.anotacaoDAO = new AnotacaoDAO();
    }
    
    public void cadastrarAnotacao(String nome, String texto) throws SQLException {
        Anotacao anotacao = new Anotacao(nome, texto);
        anotacaoDAO.cadastrarAnotacao(anotacao);
    }
    
    public void editarAnotacao(Anotacao anotacao, String texto) throws SQLException {
        anotacao.editarTexto(texto);
        anotacaoDAO.atualizarAnotacao(anotacao);
    }
    
    public void excluirAnotacao(Anotacao anotacao) throws SQLException {
        int idAnotacao = anotacao.getId();
        anotacaoDAO.excluirAnotacao(idAnotacao);
    }
    
    public void lerAnotacao() throws SQLException {
        
    }
}