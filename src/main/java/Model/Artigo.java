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
 * Representa um artigo e seus dados bibliográficos obtidos pelo DOI.
 */
@Getter
@Setter
public class Artigo {
    public static final String NAO_LIDO = "Não lido";
    public static final String EM_LEITURA = "Em leitura";
    public static final String LIDO = "Lido";

    private int id;
    private String doi;
    private String titulo;
    private List<String> autores = new ArrayList<>();
    private int ano;

    // Tipo informado pelo Crossref: journal-article ou proceedings-article. 
    private String tipoPublicacao;

    // Nome da revista ou título dos anais em que o trabalho foi publicado.
    private String veiculo;
    private String volume;

    // Número da edição da revista (issue)
    private String numero;

    // Identificador do artigo (article-number), como "e4150"
    private String numeroArtigo;

    private String editora;

    // Organização responsável pelo congresso
    private String organizacao;

    // Local de realização do congresso
    private String endereco;

    // Páginas ou intervalo de páginas, como "1-10"
    private String paginas;
    private int mes;
    private String resumo;
    private String url;
    private String statusLeitura = NAO_LIDO;

    // Permite montar o artigo com os dados recebidos do serviço ou do banco.
    public Artigo() {
    }

    // Inicia um artigo pelo DOI, antes do preenchimento dos metadados.
    public Artigo(String doi) {
        setDoi(doi);
    }

    // A existência do DOI será verificada pelo serviço de consulta ao Crossref.
    public void setDoi(String doi) {
        if (doi == null || doi.isBlank()) {
            throw new IllegalArgumentException("O DOI deve ser informado.");
        }

        this.doi = doi;
    }

    // O valor zero representa um ano não informado.
    public void setAno(int ano) {
        if (ano < 0) {
            throw new IllegalArgumentException("O ano não pode ser negativo.");
        }
        
        this.ano = ano;
    }

    // O valor zero representa um mês não informado.
    public void setMes(int mes) {
        if (mes < 0 || mes > 12) {
            throw new IllegalArgumentException(
                "O mês deve ser de 1 a 12, ou zero quando não informado."
            );
        }

        this.mes = mes;
    }

    // Aceita apenas os estados de leitura definidos para o sistema.
    public void setStatusLeitura(String statusLeitura) {
        if (!NAO_LIDO.equals(statusLeitura)
                && !EM_LEITURA.equals(statusLeitura)
                && !LIDO.equals(statusLeitura)) {
            throw new IllegalArgumentException(
                "O status deve ser Não lido, Em leitura ou Lido."
            );
        }

        this.statusLeitura = statusLeitura;
    }

    // Retorna uma cópia que não permite adicionar ou remover autores.
    public List<String> getAutores() {
        return List.copyOf(autores);
    }

    public void setAutores(List<String> autores) {
        this.autores = copiarListaValidada(autores, "autores");
    }

    
    // Dados ausentes viram uma lista vazia; os elementos informados são validados.
    private List<String> copiarListaValidada(List<String> itens, String campo) {
        if (itens == null) {
            return new ArrayList<>();
        }

        for (String item : itens) {
            if (item == null || item.isBlank()) {
                throw new IllegalArgumentException(
                    "A lista de " + campo + " não pode conter valores vazios."
                );
            }
        }

        return new ArrayList<>(itens);
    }
}
