/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AnotacaoDAO;
import Model.Anotacao;
import java.time.LocalDate;

/**
 *
 * @author pedro
 */
public class AnotacaoCtrl {
    
    public Anotacao cadastrarAnotacao(String nome, String texto){
        // Anotacao vai de 1 para 1
        
        LocalDate dataCriacao = LocalDate.now();
        LocalDate dataEdicao = null;
        Anotacao anotacao = new Anotacao(texto, dataCriacao);
        AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
        
        anotacaoDAO.cadastrarAnotacao(anotacao);
        
        return anotacao;
    }
    
    public void editarAnotacao(Anotacao anotacao, String texto){
        LocalDate dataEdicao = LocalDate.now();
        anotacao.setTexto(texto);
        AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
        
        anotacaoDAO.atualizarAnotacao(anotacao);
    }
    
    public void excluirAnotacao(Anotacao anotacao){
        int idAnotacao = anotacao.getId();
        AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
        
        anotacaoDAO.excluirAnotacao(idAnotacao);
    }
    
    
} 
