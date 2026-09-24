/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Anotacao;
import java.time.LocalDate;

/**
 *
 * @author pedro
 */
public class AnotacaoCtrl {
    
    public Anotacao cadastrarAnotacao(String texto){
        // Anotacao vai de 1 para 1
        
        LocalDate dataCriacao = LocalDate.now();
        LocalDate dataEdicao = null;
        Anotacao anotacao = new Anotacao(texto, dataCriacao);
        // Chama função DAO para salvar no banco ...
        return anotacao;
    }
    
    public void editarAnotacao(Anotacao anotacao, String texto){
        LocalDate dataEdicao = LocalDate.now();
        anotacao.setTexto(texto);
 
        // chama função DAO para atualizar no banco
        
    }
    
    public void excluirAnotacao(Anotacao anotacao){
        int idAnotacao = anotacao.getId();
        // chama função dao que recebe id e apaga a anotação correspondente
    }
    
    
} 
