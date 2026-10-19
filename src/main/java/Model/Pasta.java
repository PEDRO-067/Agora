/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa uma pasta que organiza artigos cadastrados.
 */
@Getter
@Setter
public class Pasta {
    private int id;
    private String nome;
    private final List<Artigo> artigos = new ArrayList<>();

    // Cria uma pasta vazia pelo nome.
    public Pasta(String nome) {
        setNome(nome);
    }

    // Cria uma pasta com o identificador e o nome informados.
    public Pasta(int id, String nome) {
        this(nome);
        this.id = id;
    }

    // Altera o nome após validá-lo.
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    // Adiciona um artigo e retorna false se o ID já estiver na pasta. 
    public boolean adicionarArtigo(Artigo artigo) {
        if (artigo == null) {
            throw new IllegalArgumentException("O artigo deve ser informado.");
        }

        validarIdArtigo(artigo.getId());

        for (Artigo existente : artigos) {
            if (existente.getId() == artigo.getId()) {
                return false;
            }
        }

        return artigos.add(artigo);
    }

    // Remove o vínculo em memória e retorna false se o ID não estiver na pasta.
    public boolean removerArtigo(int idArtigo) {
        validarIdArtigo(idArtigo);
        return artigos.removeIf(artigo -> artigo.getId() == idArtigo);
    }

    // Retorna uma cópia da lista que não permite adicionar ou remover itens.
    public List<Artigo> getArtigos() {
        return List.copyOf(artigos);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome da pasta não pode estar vazio."
            );
        }
    }

    private void validarIdArtigo(int idArtigo) {
        if (idArtigo <= 0) {
            throw new IllegalArgumentException(
                "O artigo deve estar cadastrado e possuir um ID maior que zero."
            );
        }
    }
}
