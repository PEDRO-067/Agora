/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Representa uma anotação associada a um artigo.
 */

@Getter
@AllArgsConstructor
public class Anotacao {
    
    @Setter
    private int id;
    
    @Setter
    private String nome;
    
    private String texto;
    private LocalDate dataCriacao;
    private LocalDate dataEdicao;
    private int artigoId;

    // A anotação a partir dos dados registrados no banco pode ser instanciada 
    // usando o contrutor com todos os argumentos do lombok. 

    // Cria uma anotação com nome e texto, usando a data atual.
    public Anotacao(String nome, String texto, int artigoId) {
        this.nome = nome.trim();
        setTexto(texto);
        this.dataCriacao = LocalDate.now();
        setArtigoId(artigoId);
    }

    //Altera o texto e registra a data da edição.
    public void editarTexto(String novoTexto) {
        setTexto(novoTexto);
        this.dataEdicao = LocalDate.now();
    }

    // Valida o texto e atribui valor a texto.
    private void setTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                "O texto da anotação não pode estar vazio."
            );
        }
        
        this.texto = texto.trim();
    }
    
    private void setArtigoId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                "O ID do artigo deve existir e ser válido."
            );
        }
        
        this.artigoId = id;
    }
}

