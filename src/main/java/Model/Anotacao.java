/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa uma anotação associada a um artigo.
 */
@Getter
@Setter
public class Anotacao {
    private int id;
    private String nome;
    private String texto;
    private LocalDate dataCriacao;
    private LocalDate dataEdicao;

    // Permite reconstruir uma anotação a partir dos dados do banco.
    public Anotacao() {
    }

    // Cria uma anotação com texto e data de criação.
    public Anotacao(String texto, LocalDate dataCriacao) {
        validarTexto(texto);

        if (dataCriacao == null) {
            throw new IllegalArgumentException(
                "A data de criação deve ser informada."
            );
        }

        this.texto = texto;
        this.dataCriacao = dataCriacao;
    }

    // Cria uma anotação com nome e texto, usando a data atual.
    public Anotacao(String nome, String texto) {
        this(texto, LocalDate.now());
        this.nome = nome;
    }

    //Altera o texto e registra a data da edição.
    public void editarTexto(String novoTexto) {
        validarTexto(novoTexto);

        this.texto = novoTexto;
        this.dataEdicao = LocalDate.now();
    }

    //Valida o texto
    private void validarTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                "O texto da anotação não pode estar vazio."
            );
        }
    }

    //Define o texto com validação sem registrar uma edição.
    public void setTexto(String texto) {
        validarTexto(texto);
        this.texto = texto;
    }

}

