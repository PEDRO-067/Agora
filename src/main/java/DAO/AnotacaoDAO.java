/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Anotacao;

/**
 *
 * @author gyudi
 */
public class AnotacaoDAO {
    
    public void cadastrarAnotacao(Anotacao anotacao) {
        String SQL = "INSERT INTO anotacao (nome, texto) VALUES (?, ?)";
        
    }
    
    public void atualizarAnotacao(Anotacao anotacao) {
        String SQL = "UPDATE anotacao SET nome = ?, texto = ? WHERE id = ?";
    }
    
    public void excluirAnotacao(int id) {
        String SQL = "DELETE FROM anotacao WHERE id = ?";
    }
    
}
