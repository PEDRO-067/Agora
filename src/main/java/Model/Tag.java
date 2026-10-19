/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa uma tag usada para classificar artigos.
 */
@Getter
@Setter
public class Tag {
    private int id;
    private String nome;

    // Cria uma tag pelo nome. 
    public Tag(String nome) {
        setNome(nome);
    }

    // Cria uma tag com o identificador e o nome informados.
    public Tag(int id, String nome) {
        this(nome);
        this.id = id;
    }

    // Altera o nome após validá-lo. 
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    // Impede nomes nulos, vazios ou compostos apenas por espaços.
    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome da tag não pode estar vazio."
            );
        }
    }
}
