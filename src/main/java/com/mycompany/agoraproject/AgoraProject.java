/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.agoraproject;

import Controller.AnotacaoCtrl;
import Model.Anotacao;
import java.time.LocalDate;

/**
 *
 * 
 */
public class AgoraProject {

    public static void main(String[] args) {
        AnotacaoCtrl antc = new AnotacaoCtrl();
        Anotacao anotacao;
        anotacao = antc.cadastrarAnotacao("EStou cadastrando uma anotacação");
        
        System.out.println("DAta de CRiação: " + anotacao.getTexto());
    }
}
