/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AnotacaoDAO;
import Model.Anotacao;
import java.time.LocalDate;

/**
 * @author pedro
 */
public class AnotacaoCtrl {
    
    private AnotacaoDAO anotacaoDAO;

    public AnotacaoCtrl() {
        this.anotacaoDAO = new AnotacaoDAO();
    }
    
    public Anotacao cadastrarAnotacao(String nome, String texto){
        LocalDate dataCriacao = LocalDate.now();
        Anotacao anotacao = new Anotacao(texto, dataCriacao);
        
        anotacaoDAO.cadastrarAnotacao(anotacao);
        
        return anotacao;
    }
    
    public void editarAnotacao(Anotacao anotacao, String texto){
        LocalDate dataEdicao = LocalDate.now();
        anotacao.setTexto(texto);
        anotacao.setDataEdicao(dataEdicao); 
        
        anotacaoDAO.atualizarAnotacao(anotacao);
    }
    
    public void excluirAnotacao(Anotacao anotacao){
        int idAnotacao = anotacao.getId();
        anotacaoDAO.excluirAnotacao(idAnotacao);
    }
}