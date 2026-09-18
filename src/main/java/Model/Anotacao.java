/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * 
 */
public class Anotacao {
    private int id;
    private String texto;
    private LocalDate dataCriacao;
    private LocalDate daataEdicao;

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDaataEdicao() {
        return daataEdicao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDaataEdicao(LocalDate daataEdicao) {
        this.daataEdicao = daataEdicao;
    }
   
}

