/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agoraproject;

import Controller.ArtigoCtrl;
import Model.Artigo;
import java.util.List;

/**
 *
 * 
 */
public class AgoraProject {

    public static void main(String[] args) throws Exception {
        ArtigoCtrl ctrl = new ArtigoCtrl();

//        System.out.println("1. Consultando a biblioteca...");
//        System.out.println("Artigos antes do cadastro: " + ctrl.listar().size());
//
//        System.out.println("2. Cadastrando por DOI...");
//        Artigo cadastrado = ctrl.cadastrarPorDoi("10.1109/5.726791");
//        System.out.println("ID: " + cadastrado.getId());
//
//        System.out.println("3. Lendo o artigo de volta do banco...");
//        Artigo recuperado = ctrl.buscarPorId(cadastrado.getId()).orElseThrow();
//        System.out.println("Título: " + recuperado.getTitulo());
//        System.out.println("Autores: " + recuperado.getAutores());
//        System.out.println("Status: " + recuperado.getStatusLeitura());
//
//        System.out.println("4. Tentando cadastrar o mesmo DOI como link...");
//        Artigo repetido = ctrl.cadastrarPorDoi("https://doi.org/10.1109/5.726791");
//        System.out.println("Mesmo ID: " + (cadastrado.getId() == repetido.getId()));
//        System.out.println("Artigos depois do cadastro: " + ctrl.listar().size());

        //Artigo test = ctrl.buscarPorId(2).orElseThrow();
        Artigo test = ctrl.listar().get(1);
        
        System.out.println(test.getId());
        System.out.println(test.getTitulo());
        System.out.println(test.getDoi());
        System.out.println(test.getAutores());
        System.out.println(test.getAno());
        System.out.println(test.getStatusLeitura());
        
    }
}
